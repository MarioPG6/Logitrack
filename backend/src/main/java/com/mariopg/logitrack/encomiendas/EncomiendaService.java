package com.mariopg.logitrack.encomiendas;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mariopg.logitrack.user.User;
import com.mariopg.logitrack.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class EncomiendaService {

    private final EncomiendaRepository encomiendaRepository;
    private final UserRepository userRepository;

    public EncomiendaService(EncomiendaRepository encomiendaRepository,
            UserRepository userRepository) {
        this.encomiendaRepository = encomiendaRepository;
        this.userRepository = userRepository;
    }

    // Crear encomienda (asociada al usuario logueado)
    @Transactional
    public Encomienda crearEncomienda(Encomienda encomienda) {

        if (encomienda == null) {
            throw new RuntimeException("La encomienda no puede ser nula.");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // viene del JWT

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        encomienda.setUser(user);
        encomienda.setEstado(EstadoEncomienda.REGISTRADO);

        return encomiendaRepository.save(encomienda);
    }

    // Listar todas (trabajador/admin)
    @PreAuthorize("hasAuthority('TRABAJADOR') or hasAuthority('ADMINISTRADOR')")
    public List<Encomienda> listarEncomiendas() {
        return encomiendaRepository.findAll();
    }

    // Buscar por ID
    @SuppressWarnings("null")
    public Encomienda obtenerPorId(Integer id) {
        return encomiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la encomienda con ID: " + id));
    }

    // Actualizar encomienda (cliente solo la suya y si no está enviada)
    @SuppressWarnings("null")
    @Transactional
    public Encomienda actualizarEncomiendaCliente(Integer id, Encomienda nuevaData) {

        if (nuevaData == null) {
            throw new RuntimeException("Los datos enviados no pueden ser nulos.");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String rol = auth.getAuthorities().iterator().next().getAuthority();

        return encomiendaRepository.findById(id)
                .map(encomienda -> {

                    // Validar dueño si es cliente
                    if (rol.equals("CLIENTE") && !encomienda.getUser().getEmail().equals(email)) {
                        throw new RuntimeException("No tienes permiso para modificar esta encomienda.");
                    }

                    // Bloquear si está enviada
                    if (encomienda.getEstado() == EstadoEncomienda.ENVIADO) {
                        throw new RuntimeException("La encomienda ya fue enviada y no se puede modificar.");
                    }

                    // Campos básicos permitidos
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

    // Eliminar (cliente solo la suya)
    @SuppressWarnings("null")
    @Transactional
    public void eliminarEncomienda(Integer id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String rol = auth.getAuthorities().iterator().next().getAuthority();

        Encomienda encomienda = encomiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: encomienda no encontrada con ID " + id));

        if (rol.equals("CLIENTE") && !encomienda.getUser().getEmail().equals(email)) {
            throw new RuntimeException("No tienes permiso para eliminar esta encomienda.");
        }

        encomiendaRepository.delete(encomienda);
    }

    // Listar solo mis encomiendas (cliente)
    public List<Encomienda> listarMisEncomiendas() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return encomiendaRepository.findByUserEmail(email);
    }

    // Listar por usuario (admin/trabajador)
    @PreAuthorize("hasAuthority('TRABAJADOR') or hasAuthority('ADMINISTRADOR')")
    public List<Encomienda> listarPorUsuario(Integer userId) {

        List<Encomienda> encomiendas = encomiendaRepository.findByUserId(userId);

        if (encomiendas.isEmpty()) {
            throw new RuntimeException("El usuario no tiene encomiendas registradas.");
        }

        return encomiendas;
    }
}
