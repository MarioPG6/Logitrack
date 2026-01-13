package jwt.demo.jwt.Cliente;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EncomiendaRepository extends JpaRepository<Encomienda, Integer> {
    List<Encomienda> findByUserId(Integer userId);
}