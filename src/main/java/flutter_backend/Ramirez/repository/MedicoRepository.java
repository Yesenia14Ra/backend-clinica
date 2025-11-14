package flutter_backend.Ramirez.repository;

import flutter_backend.Ramirez.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para la entidad Medico
 * Proporciona métodos para realizar operaciones CRUD sobre médicos
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, String> {

    /**
     * Busca un médico por su CMP
     * @param medCmp CMP del médico
     * @return Optional con el médico si existe
     */
    Optional<Medico> findByMedCmp(String medCmp);

    /**
     * Busca médicos por especialidad
     * @param espeNombre Nombre de la especialidad
     * @return Lista de médicos con esa especialidad
     */
    List<Medico> findByEspeNombre(String espeNombre);

    /**
     * Verifica si existe un médico con el CMP proporcionado
     * @param medCmp CMP del médico
     * @return true si existe, false en caso contrario
     */
    boolean existsByMedCmp(String medCmp);
}
