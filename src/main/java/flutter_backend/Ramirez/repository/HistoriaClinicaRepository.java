package flutter_backend.Ramirez.repository;

import flutter_backend.Ramirez.entity.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository para la entidad HistoriaClinica
 * Proporciona métodos para realizar operaciones CRUD sobre historias clínicas
 */
@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {

    /**
     * Busca todas las historias clínicas de un paciente específico
     * @param pacDni DNI del paciente
     * @return Lista de historias clínicas del paciente
     */
    @Query("SELECT h FROM HistoriaClinica h WHERE h.paciente.pacDni = :pacDni ORDER BY h.histFechaAtencion DESC")
    List<HistoriaClinica> findByPacientePacDni(@Param("pacDni") String pacDni);

    /**
     * Busca todas las historias clínicas atendidas por un médico específico
     * @param medCmp CMP del médico
     * @return Lista de historias clínicas del médico
     */
    @Query("SELECT h FROM HistoriaClinica h WHERE h.medico.medCmp = :medCmp ORDER BY h.histFechaAtencion DESC")
    List<HistoriaClinica> findByMedicoMedCmp(@Param("medCmp") String medCmp);

    /**
     * Busca historias clínicas por rango de fechas
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de historias clínicas en el rango de fechas
     */
    @Query("SELECT h FROM HistoriaClinica h WHERE h.histFechaAtencion BETWEEN :fechaInicio AND :fechaFin ORDER BY h.histFechaAtencion DESC")
    List<HistoriaClinica> findByFechaAtencionBetween(
        @Param("fechaInicio") LocalDate fechaInicio, 
        @Param("fechaFin") LocalDate fechaFin
    );

    /**
     * Busca historias clínicas por paciente y médico
     * @param pacDni DNI del paciente
     * @param medCmp CMP del médico
     * @return Lista de historias clínicas
     */
    @Query("SELECT h FROM HistoriaClinica h WHERE h.paciente.pacDni = :pacDni AND h.medico.medCmp = :medCmp ORDER BY h.histFechaAtencion DESC")
    List<HistoriaClinica> findByPacienteAndMedico(
        @Param("pacDni") String pacDni, 
        @Param("medCmp") String medCmp
    );
}
