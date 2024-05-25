package ridi.modelos.persistence;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter@Setter
@Entity(name = "discos_duros")
public class DiscoDuro extends Componente {
    Long capacidad;
    Boolean cargado;
    Integer tipo_datos;
    String modelo;
    Integer particiones;

    @Override
    public @NonNull Instancias instanciado() {
        return Instancias.DISCO_DURO;
    }
}
