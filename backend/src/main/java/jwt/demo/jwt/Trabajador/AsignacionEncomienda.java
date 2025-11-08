package jwt.demo.jwt.Trabajador;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jwt.demo.jwt.Cliente.Encomienda;
import jwt.demo.jwt.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asignaciones_encomienda")
public class AsignacionEncomienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "encomienda_id", nullable = false)
    private Encomienda encomienda;

    @ManyToOne
    @JoinColumn(name = "trabajador_id", nullable = false)
    private User trabajador;

    @Column(nullable = false)
    private LocalDateTime fechaAsignacion;

    @Column(nullable = false)
    private String estado; 
}
