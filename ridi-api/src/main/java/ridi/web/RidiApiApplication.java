package ridi.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "ridi.modelos.persistence")
public class RidiApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RidiApiApplication.class, args);
    }
}
