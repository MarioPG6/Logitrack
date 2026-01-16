package com.mariopg.logitrack.encomiendas;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EncomiendaService {

    private final EncomiendaRepository encomiendaRepository;

    public EncomiendaService(EncomiendaRepository encomiendaRepository) {
        this.encomiendaRepository = encomiendaRepository;
    }

    // Crear encomienda
    @Transactional
    public Encomienda crearEncomienda(Encomienda encomienda) {

        if (encomienda == null) {
            throw new RuntimeException("La encomienda no puede ser nula.");
        }

        try {
            return encomiendaRepository.save(encomienda);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la encomienda. Verifique los datos enviados.");
        }
    }

    // Listar todas
    public List<Encomienda> listarEncomiendas() {
        return encomiendaRepository.findAll();
    }

    // Buscar por ID
    @SuppressWarnings("null")
    public Encomienda obtenerPorId(Integer id) {
        return encomiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la encomienda con ID: " + id));
    }

    // Actualizar encomienda - cliente (solo datos básicos)
    @SuppressWarnings("null")
    @Transactional
    public Encomienda actualizarEncomiendaCliente(Integer id, Encomienda nuevaData) {

        if (nuevaData == null) {
            throw new RuntimeException("Los datos enviados no pueden ser nulos.");
        }

        return encomiendaRepository.findById(id)
                .map(encomienda -> {

                    // Bloquear si ya fue enviada
                    if (encomienda.getEstado() == EstadoEncomienda.ENVIADO) {
                        throw new RuntimeException("La encomienda ya fue enviada y no se puede modificar.");
                    }

                    if (nuevaData.getNombre() != null)
                        encomienda.setNombre(nuevaData.getNombre());

                    if (nuevaData.getTelefono() != null)
                        encomienda.setTelefono(nuevaData.getTelefono());

                    if (nuevaData.getEmail() != null)
                        encomienda.setEmail(nuevaData.getEmail());

                    if (nuevaData.getDireccion() != null)
                        encomienda.setDireccion(nuevaData.getDireccion());

                    if (nuevaData.getCiudadDestino() != null)
                        encomienda.setCiudadDestino(nuevaData.getCiudadDestino());

                    return encomiendaRepository.save(encomienda);
                })
                .orElseThrow(
                        () -> new RuntimeException("No se puede actualizar: encomienda no encontrada con ID " + id));
    }

    // Eliminar
    @SuppressWarnings("null")
    @Transactional
    public void eliminarEncomienda(Integer id) {

        if (!encomiendaRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: encomienda no encontrada con ID " + id);
        }

        encomiendaRepository.deleteById(id);
    }

    // Listar por usuario
    public List<Encomienda> listarPorUsuario(Integer userId) {

        List<Encomienda> encomiendas = encomiendaRepository.findByUserId(userId);

        if (encomiendas.isEmpty()) {
            throw new RuntimeException("El usuario no tiene encomiendas registradas.");
        }

        return encomiendas;
    }
}
