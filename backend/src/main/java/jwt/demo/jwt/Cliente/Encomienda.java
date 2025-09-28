package jwt.demo.jwt.Cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "encomiendas")
public class Encomienda {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String cedula;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String tipoProducto; // Documentos o Paquetes

    @Column(nullable = false)
    private String ciudadOrigen;

    @Column(nullable = false)
    private String ciudadDestino;

    @Column(nullable = false)
    private String formaPago;

    @Column(nullable = false)
    private String tiempo; // Ej: Express, Normal

    @Column(nullable = false)
    private Double valorDeclarado;

    // Relación con User (quién creó la encomienda)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) 
    private User user;
}
