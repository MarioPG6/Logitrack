package jwt.demo.jwt.Cliente.Reportes;
import lombok.Data;

@Data
public class ReporteProblematicaRequest {
    private Integer encomiendaId;
    private Integer trabajadorId;
    private String tipo;
    private String descripcion;
    private String fechaReporte;
}
