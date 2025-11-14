package flutter_backend.Ramirez.controller;

import flutter_backend.Ramirez.dto.PacienteUpdateDTO;
import flutter_backend.Ramirez.entity.Paciente;
import flutter_backend.Ramirez.repository.PacienteRepository;
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
 * Controlador REST para la gestión de Pacientes
 */
@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    /**
     * GET /api/pacientes
     * Lista todos los pacientes
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodos() {
        try {
            List<Paciente> pacientes = pacienteRepository.findAll();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Pacientes obtenidos exitosamente");
            response.put("data", pacientes);
            response.put("count", pacientes.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return manejarError("Error al listar pacientes", e);
        }
    }

    /**
     * GET /api/pacientes/{dni}
     * Obtiene un paciente por su DNI
     */
    @GetMapping("/{dni}")
    public ResponseEntity<Map<String, Object>> obtenerPorDni(@PathVariable String dni) {
        try {
            Paciente paciente = pacienteRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con DNI: " + dni));
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Paciente encontrado");
            response.put("data", paciente);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return manejarError("Error al obtener paciente", e);
        }
    }

    /**
     * POST /api/pacientes
     * Registra un nuevo paciente
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> registrar(
            @Valid @RequestBody Paciente paciente,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                .body(construirRespuestaErrorValidacion(bindingResult));
        }

        try {
            if (pacienteRepository.existsById(paciente.getPacDni())) {
                throw new RuntimeException("Ya existe un paciente con el DNI: " + paciente.getPacDni());
            }
            
            Paciente pacienteGuardado = pacienteRepository.save(paciente);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Paciente registrado exitosamente");
            response.put("data", pacienteGuardado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return manejarError("Error al registrar paciente", e);
        }
    }

    /**
     * PUT /api/pacientes/{dni}
     * Actualiza un paciente existente
     */
    @PutMapping("/{dni}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @PathVariable String dni,
            @Valid @RequestBody PacienteUpdateDTO pacienteActualizado,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                .body(construirRespuestaErrorValidacion(bindingResult));
        }

        try {
            Paciente paciente = pacienteRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con DNI: " + dni));
            
            // Actualizar solo los campos permitidos (no el DNI)
            paciente.setPacNombre(pacienteActualizado.getPacNombre());
            paciente.setPacApellidoPaterno(pacienteActualizado.getPacApellidoPaterno());
            paciente.setPacApellidoMaterno(pacienteActualizado.getPacApellidoMaterno());
            paciente.setPacDireccion(pacienteActualizado.getPacDireccion());
            paciente.setPacTelefono(pacienteActualizado.getPacTelefono());
            
            Paciente pacienteGuardado = pacienteRepository.save(paciente);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Paciente actualizado exitosamente");
            response.put("data", pacienteGuardado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return manejarError("Error al actualizar paciente", e);
        }
    }

    /**
     * DELETE /api/pacientes/{dni}
     * Elimina un paciente
     */
    @DeleteMapping("/{dni}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable String dni) {
        try {
            if (!pacienteRepository.existsById(dni)) {
                throw new RuntimeException("Paciente no encontrado con DNI: " + dni);
            }
            pacienteRepository.deleteById(dni);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Paciente eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return manejarError("Error al eliminar paciente", e);
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
