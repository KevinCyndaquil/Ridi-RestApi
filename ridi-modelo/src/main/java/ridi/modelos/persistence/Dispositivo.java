package ridi.modelos.persistence;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;
import ridi.modelos.util.groups.IdInfo;
import ridi.modelos.util.groups.NotId;
import ridi.modelos.util.Unico;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.InheritanceType.JOINED;

@JsonTypeInfo(use = NAME, property = "instanciado")
@JsonSubTypes({
        @Type(value = Computadora.class, name = "COMPUTADORA")
})

@Data
@Entity(name = "dispositivos")
@Inheritance(strategy = JOINED)
public abstract class Dispositivo implements Unico<UUID> {
    @Null(groups = NotId.class, message = "No se requiere un ID")
    @NotNull(groups = IdInfo.class, message = "Se requiere un ID")
    @Id @GeneratedValue(strategy = GenerationType.AUTO) UUID id;
    String no_serie;
    String fabricante;
    @ManyToOne Sucursal sucursal;
    String descripcion;
    String estatus;
    @Getter(AccessLevel.NONE)@Setter(AccessLevel.NONE)
    @Enumerated(STRING) Instancias instanciado = instanciado();

    public enum Instancias {
        COMPUTADORA,
        PERIFERICO,
        MEMORIA_RAM,
        DISCO_DURO,
        PROCESADOR
    }

    @JsonGetter("instanciado")
    public abstract @NonNull Instancias instanciado();

    public boolean equals(Object obj) {
        if (obj instanceof Dispositivo dispositivo)
            if (id == null) return false;
            else return id.equals(dispositivo.id);
        return false;
    }

    @Override
    public int hashCode() {
        if (id == null) return 0;
        return id.hashCode();
    }
}
