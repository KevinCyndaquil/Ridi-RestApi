package ridi.modelos.persistence;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Check;
import ridi.modelos.util.Unico;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Data
@Entity(name = "incidencias")
@Check(constraints = "fecha_reporte < fecha_limite AND fecha_solucion > fecha_reporte")
public class Incidencia implements Unico<UUID> {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) UUID folio;
    Date fecha_reporte;
    Date fecha_limite;
    Date fecha_solucion;
    String descripcion;
    @Enumerated(STRING) Estatus estatus;
    @ManyToOne UsuarioRidi encargado;
    @ManyToMany Set<Dispositivo> dispositivos;

    @Override
    public UUID getId() {
        return folio;
    }

    public enum Estatus {
        SOLUCIONADA,
        PENDIENTE,
        ESPERA_CONFIRMACION,
        NO_REALIZADA,
        ABANDONADA
    }
}
