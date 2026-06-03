package cl.duoc.mineria.camiones.controller;

import cl.duoc.mineria.camiones.model.ActualizarEstadoCamionDTO;
import cl.duoc.mineria.camiones.model.Camion;
import cl.duoc.mineria.camiones.model.RegistrarCamionDTO;
import cl.duoc.mineria.camiones.service.CamionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/camiones")
@RequiredArgsConstructor
public class CamionController {

    private final CamionService camionService;

    @PostMapping("/registrar")
    public ResponseEntity<Camion> registrar(@Valid @RequestBody RegistrarCamionDTO dto) {
        return new ResponseEntity<>(camionService.registrarCamion(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Camion>> listarTodas() {
        return ResponseEntity.ok(camionService.listarFlota());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Camion> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(camionService.obtenerPorId(id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Camion> actualizarEstado(
            @PathVariable Long id, 
            @Valid @RequestBody ActualizarEstadoCamionDTO dto) {
        return ResponseEntity.ok(camionService.actualizarEstado(id, dto));
    }

    // Endpoint de validación externa consumido vía WebClient por otros servicios
    @GetMapping("/existe/{id}")
    public ResponseEntity<Boolean> verificarExiste(@PathVariable Long id) {
        boolean existe = camionService.existeCamion(id);
        return ResponseEntity.ok(existe);
    }
}