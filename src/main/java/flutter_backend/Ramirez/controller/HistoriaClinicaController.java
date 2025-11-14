package flutter_backend.Ramirez.controller;

import flutter_backend.Ramirez.dto.HistoriaClinicaDTO;
import flutter_backend.Ramirez.dto.HistoriaClinicaResponseDTO;
import flutter_backend.Ramirez.service.HistoriaClinicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de Historias Clínicas
 * Proporciona endpoints para el CRUD completo
 */
@RestController
@RequestMapping("/api/historias-clinicas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HistoriaClinicaController {

    private final HistoriaClinicaService historiaClinicaService;

    /**
     * GET /api/historias-clinicas
     * Lista todas las historias clínicas
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodas() {
        try {
            List<HistoriaClinicaResponseDTO> historias = historiaClinicaService.listarTodas();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Historias clínicas obtenidas exitosamente");
            response.put("data", historias);
            response.put("count", historias.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return manejarError("Error al listar historias clínicas", e);
        }
    }

    /**
     * GET /api/historias-clinicas/{id}
     * Obtiene una historia clínica por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Long id) {
        try {
            HistoriaClinicaResponseDTO historia = historiaClinicaService.obtenerPorId(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Historia clínica encontrada");
            response.put("data", historia);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return manejarError("Error al obtener historia clínica", e);
        }
    }

    /**
     * POST /api/historias-clinicas
     * Registra una nueva historia clínica
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> registrar(
            @Valid @RequestBody HistoriaClinicaDTO dto,
            BindingResult bindingResult) {
        
        // Validar errores de validación
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                .body(construirRespuestaErrorValidacion(bindingResult));
        }

        try {
            HistoriaClinicaResponseDTO historiaCreada = historiaClinicaService.registrar(dto);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Historia clínica registrada exitosamente");
            response.put("data", historiaCreada);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return manejarError("Error al registrar historia clínica", e);
        }
    }

    /**
     * PUT /api/historias-clinicas/{id}
     * Actualiza una historia clínica existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody HistoriaClinicaDTO dto,
            BindingResult bindingResult) {
        
        // Validar errores de validación
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                .body(construirRespuestaErrorValidacion(bindingResult));
        }

        try {
            HistoriaClinicaResponseDTO historiaActualizada = historiaClinicaService.actualizar(id, dto);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Historia clínica actualizada exitosamente");
            response.put("data", historiaActualizada);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return manejarError("Error al actualizar historia clínica", e);
        }
    }

    /**
     * DELETE /api/historias-clinicas/{id}
     * Elimina una historia clínica
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        try {
            historiaClinicaService.eliminar(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Historia clínica eliminada exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return manejarError("Error al eliminar historia clínica", e);
        }
    }

    /**
     * GET /api/historias-clinicas/paciente/{pacDni}
     * Busca historias clínicas por DNI del paciente
     */
    @GetMapping("/paciente/{pacDni}")
    public ResponseEntity<Map<String, Object>> buscarPorPaciente(@PathVariable String pacDni) {
        try {
            List<HistoriaClinicaResponseDTO> historias = historiaClinicaService.buscarPorPaciente(pacDni);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Historias clínicas del paciente obtenidas exitosamente");
            response.put("data", historias);
            response.put("count", historias.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return manejarError("Error al buscar historias por paciente", e);
        }
    }

    /**
     * GET /api/historias-clinicas/medico/{medCmp}
     * Busca historias clínicas por CMP del médico
     */
    @GetMapping("/medico/{medCmp}")
    public ResponseEntity<Map<String, Object>> buscarPorMedico(@PathVariable String medCmp) {
        try {
            List<HistoriaClinicaResponseDTO> historias = historiaClinicaService.buscarPorMedico(medCmp);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Historias clínicas del médico obtenidas exitosamente");
            response.put("data", historias);
            response.put("count", historias.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return manejarError("Error al buscar historias por médico", e);
        }
    }

    /**
     * GET /api/historias-clinicas/fechas?inicio={fecha}&fin={fecha}
     * Busca historias clínicas por rango de fechas
     */
    @GetMapping("/fechas")
    public ResponseEntity<Map<String, Object>> buscarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        try {
            List<HistoriaClinicaResponseDTO> historias = historiaClinicaService.buscarPorRangoFechas(inicio, fin);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Historias clínicas en el rango de fechas obtenidas exitosamente");
            response.put("data", historias);
            response.put("count", historias.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return manejarError("Error al buscar historias por rango de fechas", e);
        }
    }

    /**
     * Método helper para construir respuesta de errores de validación
     */
    private Map<String, Object> construirRespuestaErrorValidacion(BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Error de validación");
        
        List<String> errores = bindingResult.getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());
        
        response.put("errors", errores);
        return response;
    }

    /**
     * Método helper para manejar errores
     */
    private ResponseEntity<Map<String, Object>> manejarError(String mensaje, Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", mensaje);
        response.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
