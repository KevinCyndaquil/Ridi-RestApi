package ridi.modelos.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import ridi.modelos.util.Unico;

import java.sql.Timestamp;
import java.util.Set;

@Getter@Setter
@Entity(name = "computadoras")
public class Computadora extends Dispositivo {
    String nombre;
    Integer arquitectura;
    String organizacion;
    Integer maximo_procesos;
    String sistema_operativo;
    String ipv4;
    String version;
    String no_serie_bios;
    String nombre_bios;
    String fabricante_bios;
    String version_bios;

    @JsonManagedReference
    @OneToMany(mappedBy = "parte_de") Set<Componente> componentes;
    @JsonManagedReference
    @OneToMany(mappedBy = "conectado_a") Set<Periferico> perifericos;
    @JsonManagedReference
    @OneToMany(mappedBy = "computadora") Set<Historial> historial;

    @Override
    public @NonNull Instancias instanciado() {
        return Instancias.COMPUTADORA;
    }

    @Data
    @Entity(name = "historial_computadoras")
    @IdClass(Historial.ID.class)
    public static class Historial implements Unico<Historial.ID> {
        @JsonBackReference
        @Id @ManyToOne Computadora computadora;
        @Id int cns;
        @ManyToOne Sucursal sucursal;
        Timestamp fecha;
        String ipv4;
        String estatus;
        boolean en_linea;

        @Override
        public ID getId() {
            ID id = new ID();
            id.setComputadora(computadora);
            id.setCns(cns);
            return id;
        }

        @Data
        @Embeddable
        public static class ID {
            @JsonBackReference
            @ManyToOne Computadora computadora;
            int cns;
        }
    }
}
