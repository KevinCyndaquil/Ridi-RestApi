package ridi.modelos.persistence;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter@Setter
@Entity(name = "procesadores")
public class Procesador extends Componente {
    String procesador_id;
    String nombre;
    Integer velocidad;
    Integer nucleos;
    Integer nucleos_logicos;
    Integer arquitectura;
    Integer tipo;
    String cpu_estatus;

    @Override
    public @NonNull Instancias instanciado() {
        return Instancias.PROCESADOR;
    }
}
