package ridi.web.util.auth;

import lombok.NonNull;
import ridi.modelos.persistence.UsuarioRidi;

public record Credential(@NonNull String identifier,
                         @NonNull String passwd) {

    public static @NonNull Credential by(@NonNull UsuarioRidi user) {
        return new Credential(user.getCorreo(), user.getPassword());
    }
}
