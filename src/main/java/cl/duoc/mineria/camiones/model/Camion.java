package cl.duoc.mineria.camiones.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad de negocio que representa a un Camión Autónomo en la faena minera.
 */
@Entity
@Table(name = "camiones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Camion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String patente;

    @Column(name = "capacidad_tonelaje", nullable = false)
    private Double capacidadTonelaje;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_camion", nullable = false, length = 30)
    private EstadoCamion estadoCamion;
}