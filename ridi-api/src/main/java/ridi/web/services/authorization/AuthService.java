package ridi.web.services.authorization;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ridi.modelos.persistence.UsuarioRidi;
import ridi.web.services.persistence.SaveService;
import ridi.web.util.auth.Credential;
import ridi.web.util.auth.TokenEntity;

import javax.security.auth.login.CredentialException;
import javax.security.auth.login.CredentialNotFoundException;
import java.security.NoSuchAlgorithmException;

@Component
@Setter(onMethod_ = @Autowired)
public abstract class AuthService <U extends UsuarioRidi> implements SaveService<U>, UserDetailsService {
    protected EncrypService encrypService;
    protected JwtService jwtService;

    protected abstract U findByCredentialIdentifier(@NonNull String identifier) throws EntityNotFoundException;

    @Override
    @Transactional
    public U save(@NonNull U entity) {
        TokenEntity<U> tokenEntity = register(entity);
        return tokenEntity.getEntity();
    }

    @Transactional
    public TokenEntity<U> register(final @NonNull U user) {
        try {
            String salt = encrypService.salt();
            String hassPasswd = encrypService.hashPasswd(user.getPasswd(), salt);
            user.setPasswd(hassPasswd);
            user.setSalt(salt);

            U userRegistered = SaveService.super.save(user);
            String token = jwtService.generateToken(userRegistered);

            return TokenEntity.<U>builder()
                    .entity(userRegistered)
                    .token(token)
                    .expiration_date(jwtService.parse(token).getExpiration())
                    .build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ocurrió un error durante el proceso de encriptación, no se realizó el registro");
        }
    }

    public TokenEntity<U> login(final @NonNull Credential credential) throws CredentialException {
        U user = findByCredentialIdentifier(credential.identifier());

        try {
            System.out.println(credential.passwd());
            String hassPasswdProvided = encrypService.hashPasswd(credential.passwd(), user.getSalt());

            if (!hassPasswdProvided.equals(user.getPasswd()))
                throw new CredentialNotFoundException("Contraseña incorrecta");

            String token = jwtService.generateToken(user);

            return TokenEntity.<U>builder()
                    .entity(user)
                    .token(token)
                    .expiration_date(jwtService.parse(token).getExpiration())
                    .build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ocurrió un error durante el proceso de encriptación, no se puede realizar el logeo");
        }
    }
}
