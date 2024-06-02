package ridi.web.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ridi.modelos.persistence.UsuarioRidi;
import ridi.web.repositories.UsuarioRidiRepository;
import ridi.web.services.authorization.AuthService;
import ridi.web.services.persistence.FindService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioRidiService
        extends AuthService<UsuarioRidi>
        implements FindService<UsuarioRidi, UUID> {
    final UsuarioRidiRepository ridiRepository;

    @org.springframework.lang.NonNull
    @Override
    public @NonNull UsuarioRidiRepository repository() {
        return ridiRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        return findByCredentialIdentifier(correo);
    }

    @Override
    protected UsuarioRidi findByCredentialIdentifier(@NonNull String correo) throws EntityNotFoundException {
        return ridiRepository.findByCorreo(correo)
                .orElseThrow(() -> new EntityNotFoundException("Usuario con correo=%s no está registrado"
                        .formatted(correo)));
    }
}
