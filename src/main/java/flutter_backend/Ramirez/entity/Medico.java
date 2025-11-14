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
 * Entidad Medico
 * Representa la información de un médico en el sistema de gestión de historias clínicas
 */
@Entity
@Table(name = "medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

    @Id
    @Column(name = "MED_Cmp", length = 10, nullable = false)
    @NotBlank(message = "El CMP es obligatorio")
    @Pattern(regexp = "^[0-9]{4,10}$", message = "El CMP debe contener entre 4 y 10 dígitos")
    private String medCmp;

    @Column(name = "MED_Nombre", nullable = false, length = 100)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String medNombre;

    @Column(name = "MED_Apellidos", nullable = false, length = 200)
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 200, message = "Los apellidos deben tener entre 2 y 200 caracteres")
    private String medApellidos;

    @Column(name = "ESPE_Nombre", nullable = false, length = 100)
    @NotBlank(message = "La especialidad es obligatoria")
    @Size(min = 2, max = 100, message = "La especialidad debe tener entre 2 y 100 caracteres")
    private String espeNombre;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HistoriaClinica> historiasClinicas;

    /**
     * Método helper para obtener el nombre completo del médico con su especialidad
     */
    public String getNombreCompletoConEspecialidad() {
        return medNombre + " " + medApellidos + " - " + espeNombre;
    }
}
