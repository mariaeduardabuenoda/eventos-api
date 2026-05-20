package unicv.poo.eventos_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan; // Import importante!

@SpringBootApplication
@EntityScan(basePackages = "unicv.poo.eventos_api.entity")
public class EventosApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventosApiApplication.class, args);
    }
}