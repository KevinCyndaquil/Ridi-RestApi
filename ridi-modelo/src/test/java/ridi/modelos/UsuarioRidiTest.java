package ridi.modelos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;
import ridi.modelos.util.groups.IdInfo;
import ridi.modelos.util.groups.InitInfo;
import ridi.modelos.util.groups.NotId;
import ridi.modelos.persistence.UsuarioRidi;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioRidiTest {

    Validator validator;

    @BeforeEach
    void init() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void testId() {
        UsuarioRidi usuarioRidi = new UsuarioRidi();
        System.out.println(usuarioRidi.getId());

        var constrains = validator.validate(usuarioRidi, IdInfo.class);
        System.out.println("-----Unexpected constrains-----");
        constrains.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
        assertEquals(1, constrains.size());

        constrains = validator.validate(usuarioRidi, NotId.class);
        System.out.println("-----Unexpected constrains-----");
        constrains.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
        assertEquals(0, constrains.size());
    }

    @Test
    void testNames() {
        UsuarioRidi kevin = initUsuario();
        kevin.setNombres("KEVIN");

        var constrains = validator.validate(kevin, InitInfo.class);
        System.out.println("-----Unexpected constrains-----");
        constrains.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
        assertEquals(0, constrains.size());

        kevin.setNombres("KEVIN ALEJANDRO XD");

        constrains = validator.validate(kevin, InitInfo.class);
        System.out.println("-----Unexpected constrains-----");
        constrains.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
        assertEquals(0, constrains.size());

        kevin.setNombres("KeVIN");
        kevin.setApellidos("FRANCISCO GONZÁLEZa KEVIN");
        constrains = validator.validate(kevin, InitInfo.class);
        System.out.println("-----Unexpected constrains-----");
        constrains.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
        assertEquals(2, constrains.size());
    }

    @Test
    void testPhoneNumber() {
        UsuarioRidi kevin = initUsuario();

        var constrains = validator.validate(kevin, InitInfo.class);
        System.out.println("-----Unexpected constrains-----");
        constrains.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);

        assertEquals(0, constrains.size());

        kevin.setTelefono("962 185 9698");
        constrains = validator.validate(kevin, InitInfo.class);
        System.out.println("-----Expected constrains-----");
        constrains.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
        assertEquals(1, constrains.size());
    }

    @Test
    void testEmail() {
        UsuarioRidi kevin = initUsuario();

        var constrains = validator.validate(kevin, InitInfo.class);
        System.out.println("-----Unexpected constrains-----");
        constrains.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);

        assertEquals(0, constrains.size());

        kevin.setCorreo("kevin.ale.gmail.com");
        constrains = validator.validate(kevin, InitInfo.class);
        System.out.println("-----Expected constrains-----");
        constrains.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
        assertEquals(1, constrains.size());
    }

    @Test
    void testRoles() {
        //validar permiso automatico de admin
        UsuarioRidi admin = initUsuario();
        assertEquals("all", admin.getPermisos());

        //validar permisos no cambiables para un admin
        assertThrows(
                IllegalArgumentException.class,
                () -> admin.setPermisos("sc:00124;sc:00123"));

        //validar permisos para encargados
        admin.setRole(UsuarioRidi.Roles.ENCARGADO);
        admin.setPermisos("sc:00a12;sc:00a13");

        var codigosSucursal = UsuarioRidi.codigosSucursalEnPermiso(admin.getPermisos());
        System.out.println(codigosSucursal);
        assertEquals(2, codigosSucursal.size());

        var constrains = validator.validate(admin, InitInfo.class);
        constrains.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
        assertEquals(0, constrains.size());

        admin.setPermisos("s_:00");
        constrains = validator.validate(admin, InitInfo.class);
        constrains.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
        assertEquals(1, constrains.size());
    }

    private @NonNull UsuarioRidi initUsuario() {
        UsuarioRidi kevin = new UsuarioRidi();
        kevin.setNombres("KEVIN ALEJANDRO");
        kevin.setApellidos("FRANCISCO GONZÁLEZ");
        kevin.setTelefono("+(52) 962 185 9698");
        kevin.setCorreo("aleplantsvsz@gmail.com");
        kevin.setPasswd("qw6xdg7sB!");
        kevin.setRole(UsuarioRidi.Roles.ADMIN);

        return kevin;
    }
}
