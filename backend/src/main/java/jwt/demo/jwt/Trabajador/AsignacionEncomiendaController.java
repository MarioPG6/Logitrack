package jwt.demo.jwt.Trabajador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asignaciones")
public class AsignacionEncomiendaController {

    @Autowired
    private AsignacionEncomiendaService asignacionService;

    @PostMapping("/asignar")
    public AsignacionEncomienda asignarEncomienda(
            @RequestParam Integer encomiendaId,
            @RequestParam Integer trabajadorId) {
        return asignacionService.asignarEncomienda(encomiendaId, trabajadorId);
    }

    @GetMapping("/trabajador/{id}")
    public List<AsignacionEncomienda> obtenerAsignacionesPorTrabajador(@PathVariable Integer id) {
        return asignacionService.obtenerAsignacionesPorTrabajador(id);
    }
}
