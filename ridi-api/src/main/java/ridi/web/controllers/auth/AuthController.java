package ridi.web.controllers.auth;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ridi.modelos.persistence.UsuarioRidi;
import ridi.modelos.util.groups.InitInfo;
import ridi.modelos.util.groups.NotId;
import ridi.web.services.authorization.AuthService;
import ridi.web.util.auth.Credential;
import ridi.web.util.response.RidiResponse;

import javax.security.auth.login.CredentialException;

public interface AuthController <U extends UsuarioRidi> {
    @NonNull AuthService<U> service();

    @PostMapping("auth/register")
    default RidiResponse register(@Validated({InitInfo.class, NotId.class}) @RequestBody U user) {
        return RidiResponse.body()
                .status(HttpStatus.CREATED)
                .message("Usuario %s registrado correctamente"
                        .formatted(user.getCorreo()))
                .json(service().register(user))
                .build();
    }

    @PostMapping("auth/login")
    default RidiResponse login(@RequestBody Credential credential) throws CredentialException {
        return RidiResponse.body()
                .status(HttpStatus.OK)
                .message("Usuario %s logeado correctamente"
                        .formatted(credential.identifier()))
                .json(service().login(credential))
                .build();
    }
}
