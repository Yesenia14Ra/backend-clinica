package flutter_backend.Ramirez.controller;

import flutter_backend.Ramirez.dto.MedicoUpdateDTO;
import flutter_backend.Ramirez.entity.Medico;
import flutter_backend.Ramirez.repository.MedicoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de Médicos
 */
@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MedicoController {

    private final MedicoRepository medicoRepository;

    /**
     * GET /api/medicos
     * Lista todos los médicos
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodos() {
        try {
            List<Medico> medicos = medicoRepository.findAll();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Médicos obtenidos exitosamente");
            response.put("data", medicos);
            response.put("count", medicos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return manejarError("Error al listar médicos", e);
        }
    }

    /**
     * GET /api/medicos/{cmp}
     * Obtiene un médico por su CMP
     */
    @GetMapping("/{cmp}")
    public ResponseEntity<Map<String, Object>> obtenerPorCmp(@PathVariable String cmp) {
        try {
            Medico medico = medicoRepository.findById(cmp)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con CMP: " + cmp));
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Médico encontrado");
            response.put("data", medico);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return manejarError("Error al obtener médico", e);
        }
    }

    /**
     * GET /api/medicos/especialidad/{especialidad}
     * Busca médicos por especialidad
     */
    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<Map<String, Object>> buscarPorEspecialidad(@PathVariable String especialidad) {
        try {
            List<Medico> medicos = medicoRepository.findByEspeNombre(especialidad);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Médicos encontrados");
            response.put("data", medicos);
            response.put("count", medicos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return manejarError("Error al buscar médicos por especialidad", e);
        }
    }

    /**
     * POST /api/medicos
     * Registra un nuevo médico
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> registrar(
            @Valid @RequestBody Medico medico,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                .body(construirRespuestaErrorValidacion(bindingResult));
        }

        try {
            if (medicoRepository.existsById(medico.getMedCmp())) {
                throw new RuntimeException("Ya existe un médico con el CMP: " + medico.getMedCmp());
            }
            
            Medico medicoGuardado = medicoRepository.save(medico);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Médico registrado exitosamente");
            response.put("data", medicoGuardado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return manejarError("Error al registrar médico", e);
        }
    }

    /**
     * PUT /api/medicos/{cmp}
     * Actualiza un médico existente
     */
    @PutMapping("/{cmp}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @PathVariable String cmp,
            @Valid @RequestBody MedicoUpdateDTO medicoActualizado,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                .body(construirRespuestaErrorValidacion(bindingResult));
        }

        try {
            Medico medico = medicoRepository.findById(cmp)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con CMP: " + cmp));
            
            // Actualizar solo los campos permitidos (no el CMP)
            medico.setMedNombre(medicoActualizado.getMedNombre());
            medico.setMedApellidos(medicoActualizado.getMedApellidos());
            medico.setEspeNombre(medicoActualizado.getEspeNombre());
            
            Medico medicoGuardado = medicoRepository.save(medico);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Médico actualizado exitosamente");
            response.put("data", medicoGuardado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return manejarError("Error al actualizar médico", e);
        }
    }

    /**
     * DELETE /api/medicos/{cmp}
     * Elimina un médico
     */
    @DeleteMapping("/{cmp}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable String cmp) {
        try {
            if (!medicoRepository.existsById(cmp)) {
                throw new RuntimeException("Médico no encontrado con CMP: " + cmp);
            }
            medicoRepository.deleteById(cmp);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Médico eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return manejarError("Error al eliminar médico", e);
        }
    }

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

    private ResponseEntity<Map<String, Object>> manejarError(String mensaje, Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", mensaje);
        response.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
