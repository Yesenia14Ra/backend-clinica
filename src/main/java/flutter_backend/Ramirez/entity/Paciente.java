package flutter_backend.Ramirez.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Entidad Paciente
 * Representa la información de un paciente en el sistema de gestión de historias clínicas
 */
@Entity
@Table(name = "pacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @Column(name = "PAC_DNI", length = 8, nullable = false)
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe contener 8 dígitos")
    private String pacDni;

    @Column(name = "PAC_Nombre", nullable = false, length = 100)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String pacNombre;

    @Column(name = "PAC_Apellido_Paterno", nullable = false, length = 100)
    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido paterno debe tener entre 2 y 100 caracteres")
    private String pacApellidoPaterno;

    @Column(name = "PAC_Apellido_Materno", length = 100)
    @Size(max = 100, message = "El apellido materno debe tener máximo 100 caracteres")
    private String pacApellidoMaterno;

    @Column(name = "PAC_Direccion", length = 255)
    @Size(max = 255, message = "La dirección debe tener máximo 255 caracteres")
    private String pacDireccion;

    @Column(name = "PAC_Telefono", length = 15)
    @Pattern(regexp = "^[0-9]{9,15}$", message = "El teléfono debe contener entre 9 y 15 dígitos")
    private String pacTelefono;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HistoriaClinica> historiasClinicas;

    /**
     * Método helper para obtener el nombre completo del paciente
     */
    public String getNombreCompleto() {
        StringBuilder nombreCompleto = new StringBuilder(pacNombre);
        nombreCompleto.append(" ").append(pacApellidoPaterno);
        if (pacApellidoMaterno != null && !pacApellidoMaterno.isEmpty()) {
            nombreCompleto.append(" ").append(pacApellidoMaterno);
        }
        return nombreCompleto.toString();
    }
}
