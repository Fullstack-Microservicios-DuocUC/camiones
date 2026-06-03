package cl.duoc.mineria.camiones.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistrarCamionDTO {

    @NotBlank(message = "La patente no puede estar vacía")
    @Size(max = 10, message = "La patente no puede superar los 10 caracteres")
    private String patente;

    @NotNull(message = "La capacidad de tonelaje es obligatoria")
    @Positive(message = "La capacidad debe ser un valor positivo")
    private Double capacidadTonelaje;
}