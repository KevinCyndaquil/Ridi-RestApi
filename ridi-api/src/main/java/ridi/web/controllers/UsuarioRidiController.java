package ridi.web.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ridi.modelos.persistence.UsuarioRidi;
import ridi.web.controllers.auth.AuthController;
import ridi.web.controllers.persistence.FindController;
import ridi.web.services.UsuarioRidiService;

import java.util.UUID;

@RestController
@RequestMapping("usuarios_ridi")
@RequiredArgsConstructor
public class UsuarioRidiController implements AuthController<UsuarioRidi>, FindController<UsuarioRidi, UUID> {
    final UsuarioRidiService service;

    @Override
    public @NonNull UsuarioRidiService service() {
        return service;
    }
}
