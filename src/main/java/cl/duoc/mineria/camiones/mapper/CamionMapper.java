package cl.duoc.mineria.camiones.mapper;

import cl.duoc.mineria.camiones.model.Camion;
import cl.duoc.mineria.camiones.model.EstadoCamion;
import cl.duoc.mineria.camiones.model.RegistrarCamionDTO;

import org.springframework.stereotype.Component;

@Component
public class CamionMapper {

    public Camion toEntity(RegistrarCamionDTO dto) {
        if (dto == null) return null;
        
        return Camion.builder()
                .patente(dto.getPatente())
                .capacidadTonelaje(dto.getCapacidadTonelaje())
                .estadoCamion(EstadoCamion.DISPONIBLE) // Estado por defecto al registrarse
                .build();
    }
}