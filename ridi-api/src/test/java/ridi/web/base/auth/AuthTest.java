package ridi.web.base.auth;

import org.junit.jupiter.api.Test;
import ridi.web.base.RidiTest;

public interface AuthTest extends RidiTest {
    @Test void testRegister();
    @Test void testLogin();
    @Test void testToken();
}
