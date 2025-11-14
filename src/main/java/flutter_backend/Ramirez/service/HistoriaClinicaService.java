package flutter_backend.Ramirez.service;

import flutter_backend.Ramirez.dto.HistoriaClinicaDTO;
import flutter_backend.Ramirez.dto.HistoriaClinicaResponseDTO;
import flutter_backend.Ramirez.entity.HistoriaClinica;
import flutter_backend.Ramirez.entity.Medico;
import flutter_backend.Ramirez.entity.Paciente;
import flutter_backend.Ramirez.repository.HistoriaClinicaRepository;
import flutter_backend.Ramirez.repository.MedicoRepository;
import flutter_backend.Ramirez.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de Historias Clínicas
 * Contiene la lógica de negocio para el CRUD
 */
@Service
@RequiredArgsConstructor
@Transactional
public class HistoriaClinicaService {

    private final HistoriaClinicaRepository historiaClinicaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    /**
     * Lista todas las historias clínicas
     */
    public List<HistoriaClinicaResponseDTO> listarTodas() {
        return historiaClinicaRepository.findAll()
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
    }

    /**
     * Obtiene una historia clínica por su ID
     */
    public HistoriaClinicaResponseDTO obtenerPorId(Long id) {
        HistoriaClinica historia = historiaClinicaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada con ID: " + id));
        return convertirAResponseDTO(historia);
    }

    /**
     * Registra una nueva historia clínica
     */
    public HistoriaClinicaResponseDTO registrar(HistoriaClinicaDTO dto) {
        // Validar que el paciente existe
        Paciente paciente = pacienteRepository.findById(dto.getPacDni())
            .orElseThrow(() -> new RuntimeException("Paciente no encontrado con DNI: " + dto.getPacDni()));

        // Validar que el médico existe
        Medico medico = medicoRepository.findById(dto.getMedCmp())
            .orElseThrow(() -> new RuntimeException("Médico no encontrado con CMP: " + dto.getMedCmp()));

        // Crear y configurar la historia clínica
        HistoriaClinica historia = new HistoriaClinica();
        historia.setPaciente(paciente);
        historia.setMedico(medico);
        historia.setHistFechaAtencion(dto.getHistFechaAtencion() != null ? dto.getHistFechaAtencion() : LocalDate.now());
        historia.setHistDiagnostico(dto.getHistDiagnostico());
        historia.setHistAnalisis(dto.getHistAnalisis());
        historia.setHistTratamiento(dto.getHistTratamiento());

        // Guardar
        HistoriaClinica historiaGuardada = historiaClinicaRepository.save(historia);
        return convertirAResponseDTO(historiaGuardada);
    }

    /**
     * Actualiza una historia clínica existente
     */
    public HistoriaClinicaResponseDTO actualizar(Long id, HistoriaClinicaDTO dto) {
        // Verificar que la historia existe
        HistoriaClinica historia = historiaClinicaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada con ID: " + id));

        // Validar que el paciente existe
        Paciente paciente = pacienteRepository.findById(dto.getPacDni())
            .orElseThrow(() -> new RuntimeException("Paciente no encontrado con DNI: " + dto.getPacDni()));

        // Validar que el médico existe
        Medico medico = medicoRepository.findById(dto.getMedCmp())
            .orElseThrow(() -> new RuntimeException("Médico no encontrado con CMP: " + dto.getMedCmp()));

        // Actualizar campos
        historia.setPaciente(paciente);
        historia.setMedico(medico);
        historia.setHistFechaAtencion(dto.getHistFechaAtencion());
        historia.setHistDiagnostico(dto.getHistDiagnostico());
        historia.setHistAnalisis(dto.getHistAnalisis());
        historia.setHistTratamiento(dto.getHistTratamiento());

        // Guardar cambios
        HistoriaClinica historiaActualizada = historiaClinicaRepository.save(historia);
        return convertirAResponseDTO(historiaActualizada);
    }

    /**
     * Elimina una historia clínica
     */
    public void eliminar(Long id) {
        if (!historiaClinicaRepository.existsById(id)) {
            throw new RuntimeException("Historia clínica no encontrada con ID: " + id);
        }
        historiaClinicaRepository.deleteById(id);
    }

    /**
     * Busca historias clínicas por DNI del paciente
     */
    public List<HistoriaClinicaResponseDTO> buscarPorPaciente(String pacDni) {
        return historiaClinicaRepository.findByPacientePacDni(pacDni)
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
    }

    /**
     * Busca historias clínicas por CMP del médico
     */
    public List<HistoriaClinicaResponseDTO> buscarPorMedico(String medCmp) {
        return historiaClinicaRepository.findByMedicoMedCmp(medCmp)
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
    }

    /**
     * Busca historias clínicas por rango de fechas
     */
    public List<HistoriaClinicaResponseDTO> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return historiaClinicaRepository.findByFechaAtencionBetween(fechaInicio, fechaFin)
            .stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad HistoriaClinica a HistoriaClinicaResponseDTO
     */
    private HistoriaClinicaResponseDTO convertirAResponseDTO(HistoriaClinica historia) {
        HistoriaClinicaResponseDTO dto = new HistoriaClinicaResponseDTO();
        dto.setHistId(historia.getHistId());
        
        // Información del paciente
        dto.setPacDni(historia.getPaciente().getPacDni());
        dto.setPacNombreCompleto(historia.getPaciente().getNombreCompleto());
        dto.setPacTelefono(historia.getPaciente().getPacTelefono());
        
        // Información del médico
        dto.setMedCmp(historia.getMedico().getMedCmp());
        dto.setMedNombreCompleto(historia.getMedico().getMedNombre() + " " + historia.getMedico().getMedApellidos());
        dto.setMedEspecialidad(historia.getMedico().getEspeNombre());
        
        // Información de la historia clínica
        dto.setHistFechaAtencion(historia.getHistFechaAtencion());
        dto.setHistDiagnostico(historia.getHistDiagnostico());
        dto.setHistAnalisis(historia.getHistAnalisis());
        dto.setHistTratamiento(historia.getHistTratamiento());
        
        return dto;
    }
}
