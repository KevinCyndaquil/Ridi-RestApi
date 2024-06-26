package ridi.modelos.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ridi.modelos.util.Unico;

import java.sql.Timestamp;
import java.util.Set;

@Getter@Setter
@Entity(name = "perifericos")
public class Periferico extends Dispositivo {
    String nombre;

    @JsonBackReference
    @ManyToOne Computadora conectado_a;
    @JsonManagedReference
    @OneToMany(mappedBy = "periferico") Set<Historial> historial;

    @Override
    public @NonNull Instancias instanciado() {
        return Instancias.PERIFERICO;
    }

    @Data
    @Entity(name = "historial_perifericos")
    @IdClass(Historial.ID.class)
    public static class Historial implements Unico<Historial.ID> {
        @JsonBackReference
        @Id @ManyToOne Periferico periferico;
        @Id int cns;
        Timestamp fecha;
        String estatus;
        String info;

        @Override
        public ID getId() {
            ID id = new ID();
            id.setPeriferico(periferico);
            id.setCns(cns);
            return id;
        }

        @Data
        @Embeddable
        public static class ID {
            @JsonBackReference
            @ManyToOne Periferico periferico;
            int cns;
        }
    }
}
