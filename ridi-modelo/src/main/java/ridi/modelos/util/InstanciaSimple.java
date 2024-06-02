package ridi.modelos.util;

import jakarta.validation.constraints.NotNull;
import ridi.modelos.util.groups.IdInfo;

/**
 * Contiene una unica propiedad que representa un identificador (llave primaria) de una entidad.
 * @param <ID> el tipo del ID.
 */
public record InstanciaSimple <ID> (
        @NotNull(groups = IdInfo.class,
                message = "Es necesario proveer una propiedad ID")
        ID id) { }
