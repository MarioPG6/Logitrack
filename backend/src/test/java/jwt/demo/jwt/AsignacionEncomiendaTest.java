package jwt.demo.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import jwt.demo.jwt.Cliente.Encomienda;
import jwt.demo.jwt.Cliente.EncomiendaRepository;
import jwt.demo.jwt.User.Role;
import jwt.demo.jwt.User.User;
import jwt.demo.jwt.User.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jwt.demo.jwt.Trabajador.AsignacionEncomienda;
import jwt.demo.jwt.Trabajador.AsignacionEncomiendaRepository;
import jwt.demo.jwt.Trabajador.AsignacionEncomiendaService;

@ExtendWith(MockitoExtension.class)
class AsignacionEncomiendaServiceTest {

    @Mock
    private AsignacionEncomiendaRepository asignacionRepo;

    @Mock
    private EncomiendaRepository encomiendaRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private AsignacionEncomiendaService service;

    @Test
    void asignarEncomienda_DeberiaCrearAsignacionSiTodoEsCorrecto() {
        // Arrange
        Encomienda encomienda = new Encomienda();
        encomienda.setEstado("NUEVO");

        User trabajador = User.builder()
                .id(10)
                .role(Role.TRABAJADOR)
                .build();

        AsignacionEncomienda asignacionGuardada = AsignacionEncomienda.builder()
                .id(1)
                .encomienda(encomienda)
                .trabajador(trabajador)
                .estado("pendiente")
                .fechaAsignacion(LocalDateTime.now())
                .build();

        when(encomiendaRepo.findById(1)).thenReturn(Optional.of(encomienda));
        when(userRepo.findById(10)).thenReturn(Optional.of(trabajador));
        when(asignacionRepo.save(any(AsignacionEncomienda.class))).thenReturn(asignacionGuardada);

        // Act
        AsignacionEncomienda result = service.asignarEncomienda(1, 10);

        // Assert
        assertNotNull(result);
        assertEquals("ASIGNADO", encomienda.getEstado());
        verify(encomiendaRepo).save(encomienda);
        verify(asignacionRepo).save(any(AsignacionEncomienda.class));
    }

    @Test
    void asignarEncomienda_DeberiaLanzarExcepcionSiEncomiendaNoExiste() {
        when(encomiendaRepo.findById(1)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asignarEncomienda(1, 5));
        assertEquals("Encomienda no encontrada", ex.getMessage());
    }

    @Test
    void asignarEncomienda_DeberiaLanzarExcepcionSiTrabajadorNoExiste() {
        when(encomiendaRepo.findById(1)).thenReturn(Optional.of(new Encomienda()));
        when(userRepo.findById(5)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asignarEncomienda(1, 5));
        assertEquals("Trabajador no encontrado", ex.getMessage());
    }

    @Test
    void asignarEncomienda_DeberiaLanzarExcepcionSiElUsuarioNoEsTrabajador() {
        Encomienda encomienda = new Encomienda();
        User userNormal = User.builder().role(Role.CLIENTE).build();

        when(encomiendaRepo.findById(1)).thenReturn(Optional.of(encomienda));
        when(userRepo.findById(7)).thenReturn(Optional.of(userNormal));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asignarEncomienda(1, 7));
        assertEquals("El usuario no es un trabajador", ex.getMessage());
    }

    @Test
    void obtenerAsignacionesPorTrabajador_DeberiaListarAsignaciones() {
        List<AsignacionEncomienda> listaMock = List.of(new AsignacionEncomienda());

        when(asignacionRepo.findByTrabajadorId(10)).thenReturn(listaMock);

        List<AsignacionEncomienda> result = service.obtenerAsignacionesPorTrabajador(10);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(asignacionRepo).findByTrabajadorId(10);
    }
}