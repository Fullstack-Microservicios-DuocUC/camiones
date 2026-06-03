package cl.duoc.mineria.camiones.repository;

import cl.duoc.mineria.camiones.model.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CamionRepository extends JpaRepository<Camion, Long> {
    Optional<Camion> findByPatente(String patente);
}