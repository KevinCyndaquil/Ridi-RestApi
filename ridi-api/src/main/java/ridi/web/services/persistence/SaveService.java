package ridi.web.services.persistence;

import org.springframework.lang.NonNull;
import ridi.modelos.util.Unico;
import ridi.web.repositories.RidiRepository;

import java.util.Set;
import java.util.stream.Collectors;

public interface SaveService <T extends Unico<?>> {
    @NonNull RidiRepository<T, ?> repository();

    default T save(@NonNull T entity) {
        return repository().save(entity);
    }

    default Set<T> saveAll(@NonNull Set<T> entities) {
        return entities.stream()
                .map(this::save)
                .collect(Collectors.toSet());
    }
}
