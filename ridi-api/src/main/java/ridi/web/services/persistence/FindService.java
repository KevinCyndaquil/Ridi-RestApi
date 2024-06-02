package ridi.web.services.persistence;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.lang.NonNull;
import ridi.modelos.util.Unico;
import ridi.web.repositories.RidiRepository;

import java.util.List;

public interface FindService <T extends Unico<ID>, ID> {
    @NonNull RidiRepository<T, ID> repository();

    default T findById(@NonNull ID id) {
        return repository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Busqueda por id=%s sin resultados"
                        .formatted(id)));
    }

    default T findById(@NonNull T entity) {
        return findById(entity.getId());
    }

    default List<T> findAll() {
        return repository().findAll();
    }
}
