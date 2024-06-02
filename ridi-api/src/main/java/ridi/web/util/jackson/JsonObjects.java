package ridi.web.util.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public enum JsonObjects {
    USUARIO_RIDI(Objects.requireNonNull(JsonObjects.class.getClassLoader().getResource(
            "data/usuarios_ridi.json"))),;

    final URL url;

    public List<Map<String, Object>> content() {
        try {
            return new RidiMapper().readValue(url, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> content(@NonNull Class<T> classType) {
        try {
            RidiMapper mapper = new RidiMapper();
            return mapper.readValue(url, new TypeReference<>() {
                @Override
                public Type getType() {
                    return mapper.getTypeFactory().constructCollectionType(List.class, classType);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public @NonNull Optional<Map<String, Object>> first() {
        return content()
                .stream()
                .findFirst();
    }

    public <T> @NonNull Optional<T> first(@NonNull Class<T> classType) {
        return content(classType)
                .stream()
                .findFirst();
    }
}
