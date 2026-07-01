package cl.duoc.mineria.camiones.controller;

import cl.duoc.mineria.camiones.model.ActualizarEstadoCamionDTO;
import cl.duoc.mineria.camiones.model.Camion;
import cl.duoc.mineria.camiones.model.RegistrarCamionDTO;
import cl.duoc.mineria.camiones.service.CamionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/camiones")
@RequiredArgsConstructor
@Tag(name = "Gestión de Camiones", description = "Operaciones para administrar el ciclo de vida de los camiones de la flota.")
public class CamionController {

    private final CamionService camionService;

    @PostMapping("/registrar")
    @Operation(summary = "Registrar un nuevo camión", description = "Crea un nuevo camión en el sistema con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Camión registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Camion> registrar(@Valid @RequestBody RegistrarCamionDTO dto) {
        return new ResponseEntity<>(camionService.registrarCamion(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos los camiones", description = "Obtiene una lista completa de todos los camiones registrados en la flota.")
    @ApiResponse(responseCode = "200", description = "Lista de camiones obtenida con éxito")
    public ResponseEntity<List<Camion>> listarTodas() {
        return ResponseEntity.ok(camionService.listarFlota());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un camión por su ID", description = "Busca y devuelve un camión específico a partir de su ID numérico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Camión encontrado"),
            @ApiResponse(responseCode = "404", description = "Camión no encontrado con el ID proporcionado")
    })
    public ResponseEntity<Camion> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(camionService.obtenerPorId(id));
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar el estado de un camión", description = "Modifica el estado operativo de un camión existente (ej. de OPERATIVO a EN_MANTENCION).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del camión actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Camión no encontrado con el ID proporcionado")
    })
    public ResponseEntity<Camion> actualizarEstado(
            @PathVariable Long id, 
            @Valid @RequestBody ActualizarEstadoCamionDTO dto) {
        return ResponseEntity.ok(camionService.actualizarEstado(id, dto));
    }

    // Endpoint de validación externa consumido vía WebClient por otros servicios
    @GetMapping("/existe/{id}")
    @Operation(summary = "Verificar si un camión existe", description = "Endpoint de utilidad para otros microservicios. Devuelve 'true' si el camión con el ID existe, 'false' en caso contrario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificación realizada, el cuerpo de la respuesta contiene el booleano")
    })
    public ResponseEntity<Boolean> verificarExiste(@PathVariable Long id) {
        boolean existe = camionService.existeCamion(id);
        return ResponseEntity.ok(existe);
    }
}