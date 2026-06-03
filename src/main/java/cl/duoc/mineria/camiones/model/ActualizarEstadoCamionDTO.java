package cl.duoc.mineria.camiones.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ActualizarEstadoCamionDTO {

    @NotNull(message = "El nuevo estado del camión es obligatorio")
    private EstadoCamion estadoCamion;
}