package ridi.web.repositories;

import ridi.modelos.persistence.UsuarioRidi;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRidiRepository extends RidiRepository<UsuarioRidi, UUID> {
    Optional<UsuarioRidi> findByCorreo(String correo);
}
