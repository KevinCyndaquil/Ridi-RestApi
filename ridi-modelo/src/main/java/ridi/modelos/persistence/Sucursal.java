package ridi.modelos.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ridi.modelos.util.groups.IdInfo;
import ridi.modelos.util.groups.InitInfo;
import ridi.modelos.util.groups.NotId;
import ridi.modelos.util.Unico;

import java.util.UUID;

@Data
@Entity(name = "sucursales")
public class Sucursal implements Unico<UUID> {
    @Null(groups = NotId.class, message = "No se requiere un ID")
    @NotNull(groups = IdInfo.class, message = "Se requiere un ID")
    @Id @GeneratedValue(strategy = GenerationType.AUTO) UUID id;
    @NotBlank(groups = InitInfo.class, message = "Se requiere el codigo de sucursal")
    String codigo;
    @NotBlank(groups = InitInfo.class, message = "Se requiere un no mbre")
    String nombre;
    @NotNull(groups = InitInfo.class, message = "Se requiere un estado")
    @Pattern(groups = InitInfo.class,
            regexp = "(?U)^[\\p{Lu}\\p{M}\\d]+( [\\p{Lu}\\p{M}\\d]+)*$",
            message = "Formato para nombre de estado invalido")
    String estado;
    @NotNull(groups = InitInfo.class, message = "Se requiere un municipio")
    @Pattern(groups = InitInfo.class,
            regexp = "(?U)^[\\p{Lu}\\p{M}\\d]+( [\\p{Lu}\\p{M}\\d]+)*$",
            message = "Formato para nombre de municipio invalido")
    String municipio;
    @NotNull(groups = InitInfo.class, message = "Se requiere una colonia")
    @Pattern(groups = InitInfo.class,
            regexp = "(?U)^[\\p{Lu}\\p{M}\\d]+( [\\p{Lu}\\p{M}\\d]+)*$",
            message = "Formato para nombre de colonia invalido")
    String colonia;
}
