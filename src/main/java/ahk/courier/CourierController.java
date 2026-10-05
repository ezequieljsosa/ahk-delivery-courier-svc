package ahk.courier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/couriers")
public class CourierController {

    public record NewCourier(String name, double lat, double lon) {}

    public record StatusChange(String status) {}

    public record Point(double lat, double lon) {}

    private final CourierService service;

    public CourierController(CourierService service) {
        this.service = service;
    }

    @GetMapping
    public List<Courier> list() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Courier get(@PathVariable String id) {
        return service.find(id).orElseThrow(CourierController::notFound);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Courier register(@RequestBody NewCourier body) {
        return service.register(body.name(), body.lat(), body.lon());
    }

    @PutMapping("/{id}/status")
    public Courier setStatus(@PathVariable String id, @RequestBody StatusChange body) {
        return service.setStatus(id, body.status()).orElseThrow(CourierController::notFound);
    }

    /** Asigna el repartidor libre más cercano al punto. 404 si no hay ninguno libre. */
    @PostMapping("/assign")
    public Courier assign(@RequestBody Point point) {
        return service.assignNearest(point.lat(), point.lon())
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND, "no hay repartidores libres"));
    }

    private static ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "repartidor inexistente");
    }
}
