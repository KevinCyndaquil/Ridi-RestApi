package ridi.web.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ridi.modelos.persistence.UsuarioRidi;
import ridi.web.util.auth.Credential;
import ridi.web.util.auth.TokenEntity;
import ridi.web.util.jackson.JsonObjects;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthTest {
    @Autowired
    UsuarioRidiService usuarioRidiService;

    @Test
    @Transactional
    void testRegisterAndLogin() {
        UsuarioRidi admin = assertDoesNotThrow(() -> JsonObjects.USUARIO_RIDI.first(UsuarioRidi.class)
                .orElseThrow());
        admin.setPasswd("qw6xdg7sB!");

        TokenEntity<UsuarioRidi> registerToken = usuarioRidiService.register(admin);
        assertNotNull(registerToken.getEntity().getSalt());
        assertDoesNotThrow(() -> Base64.getDecoder().decode(registerToken.getEntity().getSalt()));

        System.out.printf("\t---Register Token generated: %s%n", registerToken.getToken());

        Credential credential = new Credential(
                admin.getCorreo(),
                "qw6xdg7sB!"
        );

        var loginToken = assertDoesNotThrow(() -> usuarioRidiService.login(credential));
        assertNotNull(loginToken.getEntity());

        System.out.printf("\t---Login Token generated: %s%n", loginToken.getToken());
    }
}
