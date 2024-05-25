package ridi.modelos.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Entity(name = "componentes")
public abstract class Componente extends Dispositivo {
    Long capacidad_data;
    @JsonBackReference
    @ManyToOne Computadora parte_de;
}
