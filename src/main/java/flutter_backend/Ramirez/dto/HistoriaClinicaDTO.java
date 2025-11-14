package flutter_backend.Ramirez.dto;

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
 * DTO para registrar o actualizar una Historia Clínica
 * Utilizado en los endpoints POST y PUT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaClinicaDTO {

    private Long histId;

    @NotBlank(message = "El DNI del paciente es obligatorio")
    private String pacDni;

    @NotBlank(message = "El CMP del médico es obligatorio")
    private String medCmp;

    @NotNull(message = "La fecha de atención es obligatoria")
    @PastOrPresent(message = "La fecha de atención no puede ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate histFechaAtencion;

    @NotBlank(message = "El diagnóstico es obligatorio")
    @Size(min = 10, max = 5000, message = "El diagnóstico debe tener entre 10 y 5000 caracteres")
    private String histDiagnostico;

    @Size(max = 5000, message = "El análisis debe tener máximo 5000 caracteres")
    private String histAnalisis;

    @NotBlank(message = "El tratamiento es obligatorio")
    @Size(min = 10, max = 5000, message = "El tratamiento debe tener entre 10 y 5000 caracteres")
    private String histTratamiento;
}
