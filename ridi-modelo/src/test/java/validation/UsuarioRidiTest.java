package validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ridi.groups.InitInfo;
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
    void testPhoneNumber() {
        UsuarioRidi kevin = new UsuarioRidi();
        kevin.setNombres("KEVIN ALEJANDRO");
        kevin.setApellidos("FRANCISCO GONZÁLEZ");
        kevin.setTelefono("+(52) 962 185 9698");
        kevin.setCorreo("aleplantsvsz@gmail.com");
        kevin.setPasswd("qw6xdg7sB!");

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
        UsuarioRidi kevin = new UsuarioRidi();
        kevin.setNombres("KEVIN ALEJANDRO");
        kevin.setApellidos("FRANCISCO GONZÁLEZ");
        kevin.setTelefono("+(52) 962 185 9698");
        kevin.setCorreo("aleplantsvsz@gmail.com");
        kevin.setPasswd("qw6xdg7sB!");

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
}
