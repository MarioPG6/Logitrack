package com.mariopg.logitrack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mariopg.logitrack.Cliente.Encomienda;
import com.mariopg.logitrack.Cliente.EncomiendaRepository;
import com.mariopg.logitrack.Cliente.EncomiendaService;


@ExtendWith(MockitoExtension.class)
class EncomiendaServiceTest {

    @Mock
    private EncomiendaRepository encomiendaRepository;

    @InjectMocks
    private EncomiendaService encomiendaService;

    @Test
    void crearEncomienda_DeberiaGuardarYRetornarEncomienda() {
        Encomienda encomienda = new Encomienda();
        encomienda.setNombre("Paquete 1");

        when(encomiendaRepository.save(encomienda)).thenReturn(encomienda);

        Encomienda resultado = encomiendaService.crearEncomienda(encomienda);

        assertNotNull(resultado);
        assertEquals("Paquete 1", resultado.getNombre());
        verify(encomiendaRepository).save(encomienda);
    }

    @Test
    void listarEncomiendas_DeberiaRetornarLista() {
        Encomienda e1 = new Encomienda();
        e1.setNombre("Paquete 1");

        Encomienda e2 = new Encomienda();
        e2.setNombre("Paquete 2");

        when(encomiendaRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Encomienda> resultado = encomiendaService.listarEncomiendas();

        assertEquals(2, resultado.size());
        verify(encomiendaRepository).findAll();
    }

    @Test
    void obtenerPorId_DeberiaRetornarEncomiendaSiExiste() {
        Encomienda encomienda = new Encomienda();
        encomienda.setNombre("Paquete 1");

        when(encomiendaRepository.findById(152)).thenReturn(Optional.of(encomienda));

        Optional<Encomienda> resultado = encomiendaService.obtenerPorId(152);

        assertTrue(resultado.isPresent());
        assertEquals("Paquete 1", resultado.get().getNombre());
        verify(encomiendaRepository).findById(152);
    }

    @Test
    void obtenerPorId_DeberiaRetornarVacioSiNoExiste() {
        when(encomiendaRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Encomienda> resultado = encomiendaService.obtenerPorId(1);

        assertFalse(resultado.isPresent());
        verify(encomiendaRepository).findById(1);
    }

    @Test
    void actualizarEncomienda_DeberiaActualizarSiExiste() {
        Encomienda original = new Encomienda();
        original.setNombre("Antiguo");

        Encomienda nueva = new Encomienda();
        nueva.setNombre("Nuevo");

        when(encomiendaRepository.findById(152)).thenReturn(Optional.of(original));
        when(encomiendaRepository.save(original)).thenReturn(original);

        Encomienda resultado = encomiendaService.actualizarEncomienda(152, nueva);

        assertNotNull(resultado);
        assertEquals("Nuevo", resultado.getNombre());
        verify(encomiendaRepository).findById(152);
        verify(encomiendaRepository).save(original);
    }

    @SuppressWarnings("null")
    @Test
    void actualizarEncomienda_DeberiaLanzarExcepcionSiNoExiste() {
        Encomienda nueva = new Encomienda();
        nueva.setNombre("Nuevo");

        when(encomiendaRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> encomiendaService.actualizarEncomienda(1, nueva));
        assertNotNull(thrown);
        verify(encomiendaRepository).findById(1);
        verify(encomiendaRepository, never()).save(any());
    }

    @Test
    void eliminarEncomienda_DeberiaEliminarPorId() {
        encomiendaService.eliminarEncomienda(402);
        verify(encomiendaRepository).deleteById(402);
    }

    @Test
    void listarPorUsuario_DeberiaRetornarLista() {
        Encomienda e1 = new Encomienda();
        e1.setNombre("Paquete 1");

        when(encomiendaRepository.findByUserId(1)).thenReturn(List.of(e1));

        List<Encomienda> resultado = encomiendaService.listarPorUsuario(1);

        assertEquals(1, resultado.size());
        assertEquals("Paquete 1", resultado.get(0).getNombre());
        verify(encomiendaRepository).findByUserId(1);
    }
}