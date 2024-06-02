package ridi.web;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import ridi.modelos.persistence.UsuarioRidi;
import ridi.web.services.UsuarioRidiService;
import ridi.web.base.auth.AuthTest;
import ridi.web.base.persistence.FindTest;
import ridi.web.util.Requester;
import ridi.web.util.auth.Credential;
import ridi.web.util.auth.TokenEntity;
import ridi.web.util.jackson.JsonObjects;
import ridi.web.util.jackson.RidiMapper;
import ridi.web.util.response.RidiResponse;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class UsuarioRidiTest implements AuthTest, FindTest {
    @LocalServerPort int port;

    @Autowired UsuarioRidiService service;
    @Autowired TestRestTemplate template;
    @Autowired RidiMapper mapper;

    Requester requester;

    @BeforeEach
    void init() {
        requester = new Requester(template);
    }

    @Test
    @Override
    public void testRegister() {
        var erwin = assertDoesNotThrow(() -> JsonObjects.USUARIO_RIDI
                .first()
                .orElseThrow());

        var registerResponse = requester.register(
                generateUrl("usuarios_ridi/auth/register", port),
                erwin
        );
        assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());
        assertNotNull(registerResponse.getBody());

        var token = assertDoesNotThrow(() -> registerResponse
                .getBody()
                .jsonAs(new TypeReference<TokenEntity<UsuarioRidi>>() {}));
        assertNotNull(token.getToken());
        System.out.println(token);
    }

    @Test
    @Override
    public void testLogin() {
        UsuarioRidi erwin = assertDoesNotThrow(() -> JsonObjects.USUARIO_RIDI
                .first(UsuarioRidi.class)
                .orElseThrow());
        erwin.setPasswd("erwin12Bb");

        var erwinSaved = assertDoesNotThrow(() -> service.save(erwin));

        Credential credential = new Credential(erwin.getCorreo(), "erwin12Bb");
        System.out.println("--Credential " + credential);

        var loginResponse = requester.login(
                generateUrl("usuarios_ridi/auth/login", port),
                credential
        );
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());

        var token = assertDoesNotThrow(() -> loginResponse
                .getBody()
                .jsonAs(new TypeReference<TokenEntity<UsuarioRidi>>() {}));
        assertNotNull(token.getToken());
        System.out.println("--Token " + token);

        assertEquals(erwinSaved, token.getEntity());
    }

    @Test
    @Override
    public void testToken() {
        UsuarioRidi erwin = assertDoesNotThrow(() -> JsonObjects.USUARIO_RIDI
                .first(UsuarioRidi.class)
                .orElseThrow());
        erwin.setPasswd("erwin12Bb");

        assertDoesNotThrow(() -> service.save(erwin));

        Credential credential = new Credential(erwin.getCorreo(), "erwin12Bb");
        System.out.println("--Credential " + credential);

        var loginResponse = requester.login(
                generateUrl("usuarios_ridi/auth/login", port),
                credential
        );
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());

        var token = assertDoesNotThrow(() -> loginResponse
                .getBody()
                .jsonAs(new TypeReference<TokenEntity<UsuarioRidi>>() {}));
        System.out.println("--token " + token.getToken());

        var getResponse = requester.get(
                generateUrl("computadoras", port),
                token.getToken()
        );
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());

        var unauthorizedResponse = template.getForEntity(
                generateUrl("computadoras", port),
                RidiResponse.Properties.class
        );
        assertEquals(HttpStatus.FORBIDDEN, unauthorizedResponse.getStatusCode());
    }

    @Test
    @Override
    public void testFindById() {
        var erwin = assertDoesNotThrow(() -> JsonObjects.USUARIO_RIDI
                .first(UsuarioRidi.class)
                .orElseThrow());

        var tokenEntity = assertDoesNotThrow(() -> service
                .register(erwin));
        System.out.println("--Token " + tokenEntity.getToken());

        UsuarioRidi instanciaSimple = new UsuarioRidi();
        instanciaSimple.setId(tokenEntity.getEntity().getId());
        System.out.println(service.repository().count());

        var findResponse = requester.post(
                generateUrl("usuarios_ridi/where/id-is", port),
                tokenEntity.getToken(),
                instanciaSimple
        );
        assertEquals(HttpStatus.OK, findResponse.getStatusCode());
        assertNotNull(findResponse.getBody());
        System.out.println("--Response " + findResponse);

        var object = assertDoesNotThrow(() -> findResponse
                .getBody()
                .jsonAs(UsuarioRidi.class));
        System.out.println("--body " + object);
        assertEquals(tokenEntity.getEntity(), object);
    }

    @Test
    @Override
    public void testFindAll() {

    }
}
