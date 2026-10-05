package ahk.courier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Registra unos repartidores de ejemplo si Redis está vacío. */
@Component
public class SeedData implements CommandLineRunner {

    private final CourierService service;

    public SeedData(CourierService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {
        if (!service.all().isEmpty()) {
            return;
        }
        service.register("Lucía", -34.600, -58.380);
        service.register("Martín", -34.595, -58.395);
        service.register("Sofía", -34.610, -58.375);
    }
}
