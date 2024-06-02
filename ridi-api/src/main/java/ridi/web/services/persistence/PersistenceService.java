package ridi.web.services.persistence;

import ridi.modelos.util.Unico;

public interface PersistenceService <T extends Unico<ID>, ID> extends
        SaveService<T>,
        FindService<T, ID>,
        UpdateService<T, ID> {

}
