package ridi.web.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ridi.modelos.persistence.Computadora;
import ridi.web.controllers.persistence.FindController;
import ridi.web.controllers.persistence.SaveController;
import ridi.web.services.ComputadoraService;

import java.util.UUID;

@RestController
@RequestMapping("computadoras")
@RequiredArgsConstructor
public class ComputadoraController implements SaveController<Computadora>, FindController<Computadora, UUID> {
    final ComputadoraService service;

    @Override
    public @NonNull ComputadoraService service() {
        return service;
    }
}
