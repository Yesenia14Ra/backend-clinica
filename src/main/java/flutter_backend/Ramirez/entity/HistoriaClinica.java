package flutter_backend.Ramirez.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * Entidad HistoriaClinica
 * Representa una historia clínica asociada a un paciente y un médico
 */
@Entity
@Table(name = "historias_clinicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HIST_Id")
    private Long histId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "PAC_DNI", referencedColumnName = "PAC_DNI", nullable = false)
    @NotNull(message = "El paciente es obligatorio")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "MED_Cmp", referencedColumnName = "MED_Cmp", nullable = false)
    @NotNull(message = "El médico es obligatorio")
    private Medico medico;

    @Column(name = "HIST_Fecha_Atencion", nullable = false)
    @NotNull(message = "La fecha de atención es obligatoria")
    @PastOrPresent(message = "La fecha de atención no puede ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate histFechaAtencion;

    @Column(name = "HIST_Diagnostico", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "El diagnóstico es obligatorio")
    @Size(min = 10, max = 5000, message = "El diagnóstico debe tener entre 10 y 5000 caracteres")
    private String histDiagnostico;

    @Column(name = "HIST_Analisis", columnDefinition = "TEXT")
    @Size(max = 5000, message = "El análisis debe tener máximo 5000 caracteres")
    private String histAnalisis;

    @Column(name = "HIST_Tratamiento", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "El tratamiento es obligatorio")
    @Size(min = 10, max = 5000, message = "El tratamiento debe tener entre 10 y 5000 caracteres")
    private String histTratamiento;

    /**
     * Método que se ejecuta antes de persistir la entidad
     * Establece la fecha de atención al día actual si no se proporcionó
     */
    @PrePersist
    protected void onCreate() {
        if (histFechaAtencion == null) {
            histFechaAtencion = LocalDate.now();
        }
    }
}
