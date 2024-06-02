package ridi.web.repositories;

import org.springframework.stereotype.Repository;
import ridi.modelos.persistence.Computadora;

import java.util.UUID;

@Repository
public interface ComputadoraRepository extends RidiRepository<Computadora, UUID> {
}
