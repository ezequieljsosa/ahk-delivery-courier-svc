package ahk.courier;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Los repartidores viven en Redis: courier:{id} -> hash con name, status, lat, lon couriers:all ->
 * set con todos los ids couriers:available -> set con los ids libres
 */
@Service
public class CourierService {

    private static final String ALL = "couriers:all";
    private static final String AVAILABLE = "couriers:available";

    private final StringRedisTemplate redis;

    public CourierService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public Courier register(String name, double lat, double lon) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        redis.opsForHash()
                .putAll(
                        key(id),
                        Map.of(
                                "name",
                                name,
                                "status",
                                "AVAILABLE",
                                "lat",
                                String.valueOf(lat),
                                "lon",
                                String.valueOf(lon)));
        redis.opsForSet().add(ALL, id);
        redis.opsForSet().add(AVAILABLE, id);
        return new Courier(id, name, "AVAILABLE", lat, lon);
    }

    public Optional<Courier> find(String id) {
        Map<Object, Object> hash = redis.opsForHash().entries(key(id));
        if (hash.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                new Courier(
                        id,
                        (String) hash.get("name"),
                        (String) hash.get("status"),
                        Double.parseDouble((String) hash.get("lat")),
                        Double.parseDouble((String) hash.get("lon"))));
    }

    public List<Courier> all() {
        return members(ALL).stream().map(this::find).flatMap(Optional::stream).toList();
    }

    public Optional<Courier> setStatus(String id, String status) {
        if (find(id).isEmpty()) {
            return Optional.empty();
        }
        redis.opsForHash().put(key(id), "status", status);
        if ("AVAILABLE".equals(status)) {
            redis.opsForSet().add(AVAILABLE, id);
        } else {
            redis.opsForSet().remove(AVAILABLE, id);
        }
        return find(id);
    }

    /** Elige el repartidor libre más cercano al punto dado y lo marca como ocupado. */
    public Optional<Courier> assignNearest(double lat, double lon) {
        return members(AVAILABLE).stream()
                .map(this::find)
                .flatMap(Optional::stream)
                .min(
                        (a, b) ->
                                Double.compare(
                                        Geo.km(lat, lon, a.lat(), a.lon()),
                                        Geo.km(lat, lon, b.lat(), b.lon())))
                .flatMap(c -> setStatus(c.id(), "BUSY"));
    }

    private Set<String> members(String key) {
        Set<String> ids = redis.opsForSet().members(key);
        return ids == null ? Set.of() : ids;
    }

    private static String key(String id) {
        return "courier:" + id;
    }
}
