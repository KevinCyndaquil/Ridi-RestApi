package ridi.web.util.response;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ridi.web.util.errors.Errors;
import ridi.web.util.jackson.RidiMapper;

public class RidiResponse extends ResponseEntity<RidiResponse.Properties> {

    @Builder(builderMethodName = "info", builderClassName = "ResponseInfoBuilder")
    public RidiResponse(HttpStatus status, @NonNull String message) {
        super(Properties.builder()
                .message(message)
                .build(), status);
    }

    @Builder(builderMethodName = "error", builderClassName = "ResponseErrorBuilder")
    public RidiResponse(HttpStatus status, @NonNull Errors error, @NonNull String message) {
        super(Properties.builder()
                .message(message)
                .error(error)
                .build(), status);
    }

    @Builder(builderMethodName = "body", builderClassName = "ResponseBodyBuilder")
    public RidiResponse(HttpStatus status, @NonNull String message, @NonNull Object json) {
        super(Properties.builder()
                .message(message)
                .json(json)
                .build(), status);
    }

    @Builder(builderMethodName = "resulted", builderClassName = "ResponseResultBuilder")
    public RidiResponse(HttpStatus status, @NonNull String message, @NonNull Boolean result) {
        super(Properties.builder()
                .message(message)
                .result(result)
                .build(), status);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        Errors error;
        @NonNull String message;
        Object json;
        Boolean result;

        public <T> T jsonAs(TypeReference<T> typeReference) {
            if (json == null) return null;
            return new RidiMapper().convertValue(json, typeReference);
        }

        public <T> T jsonAs(Class<T> classType) {
            if (json == null) return null;
            return new RidiMapper().convertValue(json, classType);
        }
    }
}
