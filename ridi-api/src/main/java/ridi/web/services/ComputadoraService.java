package ridi.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ridi.modelos.persistence.Computadora;
import ridi.web.repositories.ComputadoraRepository;
import ridi.web.services.persistence.PersistenceService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComputadoraService implements PersistenceService<Computadora, UUID> {
    final ComputadoraRepository repository;

    @NonNull
    @Override
    public ComputadoraRepository repository() {
        return repository;
    }
}
