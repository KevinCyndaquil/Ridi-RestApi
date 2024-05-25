package ridi.modelos.persistence;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Getter@Setter
@Check(constraints = "velocidad > 0")
public class MemoriaRam extends Componente {
    Long capacidad;
    String factor;
    String ranura;
    Integer velocidad;

    @Override
    public @NonNull Instancias instanciado() {
        return Instancias.MEMORIA_RAM;
    }
}
