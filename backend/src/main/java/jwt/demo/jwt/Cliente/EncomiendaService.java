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
    public Encomienda actualizarEncomienda(Integer id, Encomienda nuevaData) {
        return encomiendaRepository.findById(id)
                .map(encomienda -> {
                    encomienda.setNombre(nuevaData.getNombre());
                    encomienda.setCedula(nuevaData.getCedula());
                    encomienda.setTelefono(nuevaData.getTelefono());
                    encomienda.setEmail(nuevaData.getEmail());
                    encomienda.setDireccion(nuevaData.getDireccion());
                    encomienda.setTipoProducto(nuevaData.getTipoProducto());
                    encomienda.setCiudadOrigen(nuevaData.getCiudadOrigen());
                    encomienda.setCiudadDestino(nuevaData.getCiudadDestino());
                    encomienda.setFormaPago(nuevaData.getFormaPago());
                    encomienda.setTiempo(nuevaData.getTiempo());
                    encomienda.setValorDeclarado(nuevaData.getValorDeclarado());
                    encomienda.setUser(nuevaData.getUser());
                    return encomiendaRepository.save(encomienda);
                }).orElseThrow(() -> new RuntimeException("Encomienda no encontrada con ID " + id));
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