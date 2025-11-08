package jwt.demo.jwt.Trabajador;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignacionEncomiendaRepository extends JpaRepository<AsignacionEncomienda, Integer> {
    List<AsignacionEncomienda> findByTrabajadorId(Integer trabajadorId);
}

