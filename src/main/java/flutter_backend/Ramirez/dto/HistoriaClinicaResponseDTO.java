package flutter_backend.Ramirez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * DTO para respuesta de Historia Clínica con información completa
 * Incluye información del paciente y médico asociados
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaClinicaResponseDTO {

    private Long histId;
    
    // Información del paciente
    private String pacDni;
    private String pacNombreCompleto;
    private String pacTelefono;
    
    // Información del médico
    private String medCmp;
    private String medNombreCompleto;
    private String medEspecialidad;
    
    // Información de la historia clínica
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate histFechaAtencion;
    private String histDiagnostico;
    private String histAnalisis;
    private String histTratamiento;
}
