package ridi.web.base.persistence;

import org.junit.jupiter.api.Test;
import ridi.web.base.RidiTest;

public interface FindTest extends RidiTest {
    @Test void testFindById();
    @Test void testFindAll();
}
