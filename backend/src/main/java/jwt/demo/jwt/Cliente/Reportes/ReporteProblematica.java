package jwt.demo.jwt.Cliente.Reportes;

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
@Table(name = "reportes_problematica")
public class ReporteProblematica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Usuario con rol CLIENTE
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private User cliente;

    // Usuario con rol TRABAJADOR
    @ManyToOne
    @JoinColumn(name = "trabajador_id", nullable = false)
    private User trabajador;

    // Encomienda relacionada
    @ManyToOne
    @JoinColumn(name = "encomienda_id", nullable = false)
    private Encomienda encomienda;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    @Builder.Default
    private String estado = "pendiente"; // Ej: pendiente, resuelto, en revisión
}