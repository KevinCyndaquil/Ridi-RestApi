package ridi.web.controllers.persistence;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ridi.modelos.util.Unico;
import ridi.modelos.util.groups.IdInfo;
import ridi.web.services.persistence.FindService;
import ridi.web.util.response.RidiResponse;

public interface FindController <T extends Unico<ID>, ID> {
    @NonNull FindService<T, ID> service();

    @PostMapping("where/id-is")
    default RidiResponse findById(@Validated(IdInfo.class) @RequestBody T t) {
        System.out.println(t);
        return RidiResponse.body()
                .status(HttpStatus.OK)
                .message("%s encontrado correctamente"
                        .formatted(t.getClass().getSimpleName()))
                .json(service().findById(t))
                .build();
    }

    @GetMapping
    default RidiResponse findAll() {
        return RidiResponse.body()
                .status(HttpStatus.OK)
                .message("Objetos encontrado correctamente")
                .json(service().findAll())
                .build();
    }
}
