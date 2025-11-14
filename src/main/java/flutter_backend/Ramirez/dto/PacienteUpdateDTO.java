package flutter_backend.Ramirez.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar un Paciente (sin incluir el DNI)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteUpdateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String pacNombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido paterno debe tener entre 2 y 100 caracteres")
    private String pacApellidoPaterno;

    @Size(max = 100, message = "El apellido materno debe tener máximo 100 caracteres")
    private String pacApellidoMaterno;

    @Size(max = 255, message = "La dirección debe tener máximo 255 caracteres")
    private String pacDireccion;

    @Pattern(regexp = "^[0-9]{9,15}$", message = "El teléfono debe contener entre 9 y 15 dígitos")
    private String pacTelefono;
}
