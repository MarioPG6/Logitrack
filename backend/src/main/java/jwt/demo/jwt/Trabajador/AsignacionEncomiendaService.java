package jwt.demo.jwt.Trabajador;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import jwt.demo.jwt.Cliente.Encomienda;
import jwt.demo.jwt.Cliente.EncomiendaRepository;
import jwt.demo.jwt.User.User;
import jwt.demo.jwt.User.UserRepository;

@Service
public class AsignacionEncomiendaService {

    @Autowired
    private AsignacionEncomiendaRepository asignacionRepo;

    @Autowired
    private EncomiendaRepository encomiendaRepo;

    @Autowired
    private UserRepository userRepo;

    // Crear nueva asignación
    public AsignacionEncomienda asignarEncomienda(Integer encomiendaId, Integer trabajadorId) {
        Encomienda encomienda = encomiendaRepo.findById(encomiendaId)
                .orElseThrow(() -> new RuntimeException("Encomienda no encontrada"));
        User trabajador = userRepo.findById(trabajadorId)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        if (trabajador.getRole() != jwt.demo.jwt.User.Role.TRABAJADOR) {
            throw new RuntimeException("El usuario no es un trabajador");
        }

        AsignacionEncomienda asignacion = AsignacionEncomienda.builder()
                .encomienda(encomienda)
                .trabajador(trabajador)
                .fechaAsignacion(LocalDateTime.now())
                .estado("pendiente")
                .build();

        return asignacionRepo.save(asignacion);
    }

    // Listar asignaciones por trabajador
    public List<AsignacionEncomienda> obtenerAsignacionesPorTrabajador(Integer trabajadorId) {
        return asignacionRepo.findByTrabajadorId(trabajadorId);
    }
}
