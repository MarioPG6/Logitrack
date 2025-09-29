package jwt.demo.jwt.Cliente;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class EncomiendaService {

    private final EncomiendaRepository encomiendaRepository;

    public EncomiendaService(EncomiendaRepository encomiendaRepository) {
        this.encomiendaRepository = encomiendaRepository;
    }

    // Crear encomienda
    public Encomienda crearEncomienda(Encomienda encomienda) {
        return encomiendaRepository.save(encomienda);
    }

    // Listar todas
    public List<Encomienda> listarEncomiendas() {
        return encomiendaRepository.findAll();
    }

    // Buscar por ID
    public Optional<Encomienda> obtenerPorId(Integer id) {
        return encomiendaRepository.findById(id);
    }

    // Actualizar
    // EncomiendaService.java
    public Encomienda actualizarEncomienda(Integer id, Encomienda nuevaData) {
        return encomiendaRepository.findById(id)
                .map(encomienda -> {
                    if (nuevaData.getNombre() != null)
                        encomienda.setNombre(nuevaData.getNombre());
                    if (nuevaData.getCedula() != null)
                        encomienda.setCedula(nuevaData.getCedula());
                    if (nuevaData.getTelefono() != null)
                        encomienda.setTelefono(nuevaData.getTelefono());
                    if (nuevaData.getEmail() != null)
                        encomienda.setEmail(nuevaData.getEmail());
                    if (nuevaData.getDireccion() != null)
                        encomienda.setDireccion(nuevaData.getDireccion());
                    if (nuevaData.getTipoProducto() != null)
                        encomienda.setTipoProducto(nuevaData.getTipoProducto());
                    if (nuevaData.getCiudadOrigen() != null)
                        encomienda.setCiudadOrigen(nuevaData.getCiudadOrigen());
                    if (nuevaData.getCiudadDestino() != null)
                        encomienda.setCiudadDestino(nuevaData.getCiudadDestino());
                    if (nuevaData.getFormaPago() != null)
                        encomienda.setFormaPago(nuevaData.getFormaPago());
                    if (nuevaData.getTiempo() != null)
                        encomienda.setTiempo(nuevaData.getTiempo());
                    if (nuevaData.getValorDeclarado() != null)
                        encomienda.setValorDeclarado(nuevaData.getValorDeclarado());
                    if (nuevaData.getUser() != null)
                        encomienda.setUser(nuevaData.getUser());
                    if (nuevaData.getEstado() != null)
                        encomienda.setEstado(nuevaData.getEstado());
                    return encomiendaRepository.save(encomienda);
                })
                .orElseThrow(() -> new RuntimeException("Encomienda no encontrada con ID " + id));
    }

    // Eliminar
    public void eliminarEncomienda(Integer id) {
        encomiendaRepository.deleteById(id);
    }

    // Listar por usuario
    public List<Encomienda> listarPorUsuario(Integer userId) {
        return encomiendaRepository.findByUserId(userId);
    }
}