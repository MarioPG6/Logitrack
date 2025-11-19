package jwt.demo.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import jwt.demo.jwt.Cliente.Encomienda;
import jwt.demo.jwt.Cliente.Reportes.ReporteProblematica;
import jwt.demo.jwt.Cliente.Reportes.ReporteProblematicaRepository;
import jwt.demo.jwt.Cliente.Reportes.ReporteProblematicaService;
import jwt.demo.jwt.User.Role;
import jwt.demo.jwt.User.User;

@ExtendWith(MockitoExtension.class)
class ReporteProblematicaServiceTest {

    @Mock
    private ReporteProblematicaRepository repository;

    @InjectMocks
    private ReporteProblematicaService service;

    @Test
    void crearReporte_DeberiaGuardarReporteCorrectamente() {
        // Arrange
        User cliente = User.builder().id(1).role(Role.CLIENTE).build();
        User trabajador = User.builder().id(2).role(Role.TRABAJADOR).build();
        Encomienda encomienda = new Encomienda();
        String descripcion = "La encomienda llegó dañada";

        ReporteProblematica reporteGuardado = ReporteProblematica.builder()
                .cliente(cliente)
                .trabajador(trabajador)
                .encomienda(encomienda)
                .descripcion(descripcion)
                .estado("pendiente")
                .build();

        when(repository.save(any(ReporteProblematica.class))).thenReturn(reporteGuardado);

        // Act
        ReporteProblematica resultado = service.crearReporte(cliente, trabajador, encomienda, descripcion);

        // Assert
        assertNotNull(resultado);
        assertEquals("pendiente", resultado.getEstado());
        assertEquals(descripcion, resultado.getDescripcion());
        assertEquals(cliente, resultado.getCliente());
        assertEquals(trabajador, resultado.getTrabajador());
        verify(repository).save(any(ReporteProblematica.class));
    }

    @Test
    void crearReporte_DeberiaLanzarExcepcionSiClienteNoEsCliente() {
        // Arrange
        User cliente = User.builder().role(Role.TRABAJADOR).build(); // Rol incorrecto
        User trabajador = User.builder().role(Role.TRABAJADOR).build();
        Encomienda encomienda = new Encomienda();

        // Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.crearReporte(cliente, trabajador, encomienda, "desc")
        );

        assertEquals("El usuario cliente no tiene el rol CLIENTE", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void crearReporte_DeberiaLanzarExcepcionSiTrabajadorNoEsTrabajador() {
        // Arrange
        User cliente = User.builder().role(Role.CLIENTE).build();
        User trabajador = User.builder().role(Role.CLIENTE).build(); // Rol incorrecto
        Encomienda encomienda = new Encomienda();

        // Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.crearReporte(cliente, trabajador, encomienda, "desc")
        );

        assertEquals("El usuario trabajador no tiene el rol TRABAJADOR", ex.getMessage());
        verify(repository, never()).save(any());
    }
}