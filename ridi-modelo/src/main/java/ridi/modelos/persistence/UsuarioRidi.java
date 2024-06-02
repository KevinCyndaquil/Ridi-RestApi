package ridi.modelos.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ridi.annotations.Email;
import ridi.annotations.Name;
import ridi.annotations.PhoneNumber;
import ridi.modelos.util.groups.IdInfo;
import ridi.modelos.util.groups.InitInfo;
import ridi.modelos.util.groups.NotId;
import ridi.modelos.util.Unico;

import java.util.*;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@EqualsAndHashCode(exclude = {"telefono", "passwd", "salt", "role", "permisos"})
@Entity(name = "usuarios_ridi")
public class UsuarioRidi implements Unico<UUID>, UserDetails {
    @Null(groups = NotId.class, message = "No se requiere un ID")
    @NotNull(groups = IdInfo.class, message = "Se requiere un ID")
    @Id @GeneratedValue(strategy = GenerationType.AUTO) UUID id;
    @Name(groups = InitInfo.class, message = "Formato para nombres invalido")
    String nombres;
    @Name(groups = InitInfo.class, message = "Formato para apellidos invalido")
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
    @NotNull(groups = InitInfo.class, message = "Se requiere un role")
    Roles role;
    @JsonProperty(access = WRITE_ONLY)
    @NotNull(groups = InitInfo.class, message = "Se requieren los permisos")
    @Pattern(groups = InitInfo.class,
            regexp = "^all|([a-z]+:[a-zA-Z0-9]+(;[a-z]+:[a-zA-Z0-9]+)*)",
            message = "Formato de permisos invalido")
    String permisos;

    public void setRole(Roles role) {
        this.role = role;
        if (role == Roles.ADMIN) permisos = "all";
    }

    public void setPermisos(String permisos) {
        if (role == Roles.ADMIN && !permisos.equals("all"))
            throw new IllegalArgumentException(
                    "permisos otorgados a ADMIN no son adecuados, se esperaba 'all' en lugar de '%s'"
                            .formatted(permisos));
        this.permisos = permisos;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return passwd;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return correo;
    }

    public enum Roles {
        ADMIN,
        ENCARGADO
    }

    public static Set<String> codigosSucursalEnPermiso(@NonNull String permisos) {
        return Arrays.stream(permisos.split(";"))
                .filter(c -> !c.matches("sc"))
                .map(c -> c.split(":")[1])
                .collect(Collectors.toSet());
    }
}