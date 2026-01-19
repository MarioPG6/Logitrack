package com.mariopg.logitrack.encomiendas;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/encomiendas")
public class EncomiendaController {

    private final EncomiendaService encomiendaService;

    public EncomiendaController(EncomiendaService encomiendaService) {
        this.encomiendaService = encomiendaService;
    }

    // Crear encomienda
    @PostMapping
    public Encomienda crear(@RequestBody Encomienda encomienda) {
        return encomiendaService.crearEncomienda(encomienda);
    }

    // Listar todas
    @GetMapping
    public List<Encomienda> listar() {
        return encomiendaService.listarEncomiendas();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public Encomienda obtenerPorId(@PathVariable Integer id) {
        return encomiendaService.obtenerPorId(id);

    }

    // Actualizar
    @PutMapping("/{id}")
    public Encomienda actualizar(@PathVariable Integer id, @RequestBody Encomienda encomienda) {
        return encomiendaService.actualizarEncomiendaCliente(id, encomienda);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        encomiendaService.eliminarEncomienda(id);
    }

    // Listar por usuario
    @GetMapping("/usuario/{userId}")
    public List<Encomienda> listarPorUsuario(@PathVariable Integer userId) {
        return encomiendaService.listarPorUsuario(userId);
    }

    @GetMapping("/mias")
    public List<Encomienda> misEncomiendas() {
        return encomiendaService.listarMisEncomiendas();
    }

}
