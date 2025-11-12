package jwt.demo.jwt.Cliente.Reportes;
import org.springframework.stereotype.Service;

import jwt.demo.jwt.Cliente.Encomienda;
import jwt.demo.jwt.User.Role;
import jwt.demo.jwt.User.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporteProblematicaService {
     private final ReporteProblematicaRepository repository;

    public ReporteProblematica crearReporte(User cliente, User trabajador, Encomienda encomienda, String descripcion) {
        if (cliente.getRole() != Role.CLIENTE) {
            throw new IllegalArgumentException("El usuario cliente no tiene el rol CLIENTE");
        }
        if (trabajador.getRole() != Role.TRABAJADOR) {
            throw new IllegalArgumentException("El usuario trabajador no tiene el rol TRABAJADOR");
        }

        ReporteProblematica reporte = ReporteProblematica.builder()
                .cliente(cliente)
                .trabajador(trabajador)
                .encomienda(encomienda)
                .descripcion(descripcion)
                .estado("pendiente")
                .build();

        return repository.save(reporte);
    }
}
