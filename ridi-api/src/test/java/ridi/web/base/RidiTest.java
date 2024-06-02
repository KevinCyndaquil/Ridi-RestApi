package ridi.web.base;

public interface RidiTest {
    default String generateUrl(String resource, int port) {
        return "http://localhost:%s/%s".formatted(port, resource);
    }
}
