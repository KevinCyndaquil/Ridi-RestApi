package ridi.web.controllers.persistence;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ridi.modelos.util.Unico;
import ridi.modelos.util.groups.IdInfo;
import ridi.modelos.util.groups.InitInfo;
import ridi.web.services.persistence.UpdateService;
import ridi.web.util.response.RidiResponse;

public interface UpdateController<T extends Unico<ID>, ID> {
    @NonNull UpdateService<T, ID> service();

    @PutMapping
    default RidiResponse update(@Validated({InitInfo.class, IdInfo.class}) @RequestBody T t) {
        return RidiResponse.resulted()
                .status(HttpStatus.OK)
                .message("%s actualizado correctamente"
                        .formatted(t))
                .result(service().update(t))
                .build();
    }
}
