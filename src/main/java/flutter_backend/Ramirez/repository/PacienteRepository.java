package flutter_backend.Ramirez.repository;

import flutter_backend.Ramirez.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para la entidad Paciente
 * Proporciona métodos para realizar operaciones CRUD sobre pacientes
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {

    /**
     * Busca un paciente por su DNI
     * @param pacDni DNI del paciente
     * @return Optional con el paciente si existe
     */
    Optional<Paciente> findByPacDni(String pacDni);

    /**
     * Verifica si existe un paciente con el DNI proporcionado
     * @param pacDni DNI del paciente
     * @return true si existe, false en caso contrario
     */
    boolean existsByPacDni(String pacDni);
}
