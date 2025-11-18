package jwt.demo.jwt.Cliente.Reportes;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jwt.demo.jwt.Cliente.Encomienda;
import jwt.demo.jwt.Cliente.EncomiendaRepository;
import jwt.demo.jwt.User.User;
import jwt.demo.jwt.User.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/problemas")
@RequiredArgsConstructor
public class ReporteProblematicaController {

    private final ReporteProblematicaService service;
    private final UserRepository userRepository;
    private final EncomiendaRepository encomiendaRepository;
    private final ReporteProblematicaRepository repository;

    // 🔹 Crear reporte
    @PostMapping
    public ReporteProblematica crearReporte(@RequestBody ReporteProblematicaRequest request) {

        User trabajador = userRepository.findById(request.getTrabajadorId())
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        Encomienda encomienda = encomiendaRepository.findById(request.getEncomiendaId())
                .orElseThrow(() -> new RuntimeException("Encomienda no encontrada"));

        // El cliente es el dueño de la encomienda
        User cliente = encomienda.getUser();
        return service.crearReporte(
                cliente,
                trabajador,
                encomienda,
                request.getDescripcion()
        );
    }

    // 🔹 Listar reportes
    @GetMapping
    public List<ReporteProblematica> listarReportes() {
        return repository.findAll();
    }

    // 🔹 Obtener reporte por ID
    @GetMapping("/{id}")
    public ReporteProblematica obtenerPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }
}
