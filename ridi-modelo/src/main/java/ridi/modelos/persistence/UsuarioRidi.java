package ridi.modelos.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;
import ridi.annotations.Email;
import ridi.annotations.PhoneNumber;
import ridi.groups.IdInfo;
import ridi.groups.InitInfo;
import ridi.groups.NotId;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Entity(name = "usuarios_ridi")
public class UsuarioRidi {
    @Null(groups = NotId.class, message = "No se requiere un ID")
    @NotNull(groups = IdInfo.class, message = "Se requiere un ID")
    @Id UUID id;
    @NotBlank(groups = InitInfo.class, message = "Se requiere un nombre")
    @Pattern(groups = InitInfo.class,
            regexp = "(?U)^[\\p{Lu}\\p{M}]+( [\\p{Lu}\\p{M}]+)*$",
            message = "Formato para nombres invalido")
    String nombres;
    @Pattern(groups = InitInfo.class,
            regexp = "(?U)^[\\p{Lu}\\p{M}]+( [\\p{Lu}\\p{M}]+|)$",
            message = "Formato para apellidos invalido")
    String apellidos;
    @PhoneNumber(groups = InitInfo.class, message = "Se requiere un número de teléfono valido")
    String telefono;
    @Email(groups = InitInfo.class, message = "Se requiere un correo electrónico valido")
    String correo;
    @NotBlank(groups = InitInfo.class, message = "Se requiere la contraseña")
    @Pattern(groups = InitInfo.class,
            regexp = "^.{8,}$",
            message = "Longitud de contraseña de al menos 8 caracteres")
    @JsonProperty(access = WRITE_ONLY)
    String passwd;
    @JsonIgnore String salt;
}