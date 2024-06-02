package ridi.web.services.persistence;

import org.springframework.lang.NonNull;
import ridi.modelos.util.Unico;
import ridi.web.repositories.RidiRepository;

public interface UpdateService <T extends Unico<ID>, ID> {
    @NonNull RidiRepository<T, ID> repository();

    default boolean update(@NonNull T entity) {
        repository().save(entity);
        return repository().existsById(entity.getId());
    }
}
