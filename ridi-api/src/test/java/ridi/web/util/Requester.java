package ridi.web.util;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import ridi.web.util.auth.Credential;
import ridi.web.util.response.RidiResponse;

public class Requester {
    final TestRestTemplate template;

    public Requester(TestRestTemplate template) {
        this.template = template;
    }

    public ResponseEntity<RidiResponse.Properties> register(String url, Object user) {
        return template.postForEntity(
                url,
                user,
                RidiResponse.Properties.class
        );
    }

    public ResponseEntity<RidiResponse.Properties> login(String url, Credential credencial) {
        return template.postForEntity(
                url,
                credencial,
                RidiResponse.Properties.class
        );
    }

    public ResponseEntity<RidiResponse.Properties> post(
            @NonNull String url,
            @NonNull String token,
            Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);

        try {
            return template.exchange(
                    url,
                    HttpMethod.POST,
                    httpEntity,
                    RidiResponse.Properties.class
            );
        } catch (HttpClientErrorException e) {
            // Manejar errores del cliente (4xx)
            System.err.println("Client error: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            throw e;
        } catch (HttpServerErrorException e) {
            // Manejar errores del servidor (5xx)
            System.err.println("Server error: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            throw e;
        } catch (ResourceAccessException e) {
            // Manejar errores de acceso a recursos (problemas de red, etc.)
            System.err.println("Resource access error: " + e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<RidiResponse.Properties> get(
            @NonNull String url,
            @NonNull String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                RidiResponse.Properties.class
        );
    }
}
