package ridi.web.configurations;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ridi.web.util.errors.Errors;
import ridi.web.util.response.RidiResponse;

import java.util.Objects;

@RestControllerAdvice
public class RidiExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public RidiResponse handleAccessDenied() {
        return RidiResponse.error()
                .status(HttpStatus.UNAUTHORIZED)
                .error(Errors.USER_WITHOUT_GRANT_ERROR)
                .message("No tienes permiso para acceder a este recurso")
                .build();
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public RidiResponse handleNoResourceFound() {
        return RidiResponse.error()
                .status(HttpStatus.BAD_REQUEST)
                .error(Errors.WRONG_RESOURCE_ERROR)
                .message("Recurso inexistente")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RidiResponse handleIllegalArgument(@NonNull MethodArgumentNotValidException e) {
        final String message = e.getFieldError() == null ?
                "Hay un problema con la propiedad %s"
                        .formatted(e.getParameter().getParameterName()) :
                e.getFieldError().getDefaultMessage();

        return RidiResponse.error()
                .status(HttpStatus.BAD_REQUEST)
                .error(Errors.WRONG_ARGUMENT_ERROR)
                .message(Objects.requireNonNull(message))
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RidiResponse handleHttpMessageNotReadable() {
        return RidiResponse.error()
                .status(HttpStatus.BAD_REQUEST)
                .error(Errors.UNINTELIGIBLE_REQUEST_ERROR)
                .message("Cuerpo de la solicitud no pudo ser entendida por el servidor")
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public RidiResponse handleIllegalArgument(@NonNull IllegalArgumentException ex) {
        return RidiResponse.error()
                .status(HttpStatus.BAD_REQUEST)
                .error(Errors.WRONG_ARGUMENT_ERROR)
                .message("Error de argumento o parametro: " + ex.getMessage())
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public RidiResponse handleEntityNotFound(@NonNull EntityNotFoundException ex) {
        return RidiResponse.error()
                .status(HttpStatus.BAD_REQUEST)
                .error(Errors.ENTITY_NOT_FOUND_ERROR)
                .message("Busqueda sin resultado: " + ex.getMessage())
                .build();
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public RidiResponse handleCredentialsExpired(@NonNull CredentialsExpiredException ex) {
        return RidiResponse.error()
                .status(HttpStatus.UNAUTHORIZED)
                .error(Errors.USER_NOTFOUND_ERROR)
                .message("Error de credenciales: " + ex.getMessage())
                .build();
    }

    @ExceptionHandler({
            RuntimeException.class,
            NullPointerException.class
    })
    public RidiResponse handleRuntimeException() {
        return RidiResponse.error()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .error(Errors.SERVICE_ERROR)
                .message("Ocurrió un error durante el procesamiento de la solicitud")
                .build();
    }
}
