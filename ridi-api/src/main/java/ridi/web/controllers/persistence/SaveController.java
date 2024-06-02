package ridi.web.controllers.persistence;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ridi.modelos.util.Unico;
import ridi.modelos.util.groups.InitInfo;
import ridi.modelos.util.groups.NotId;
import ridi.web.services.persistence.SaveService;
import ridi.web.util.response.RidiResponse;

public interface SaveController <T extends Unico<?>> {
    @NonNull SaveService<T> service();

    @PostMapping
    default RidiResponse save(@Validated({InitInfo.class, NotId.class}) @RequestBody T t) {
        return RidiResponse.body()
                .status(HttpStatus.CREATED)
                .message("%s guardado correctamente"
                        .formatted(t.getClass().getSimpleName()))
                .json(service().save(t))
                .build();
    }
}
