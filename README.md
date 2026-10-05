# ahk-delivery-courier-svc

Repartidores y asignación. Mantiene los repartidores y su disponibilidad en Redis y asigna el repartidor libre más cercano a un punto.

Forma parte de la maqueta de delivery **ahk-delivery**. La arquitectura, los requisitos del sistema y cómo levantar todo con Docker Compose están en [ahk-delivery-infra](https://github.com/ezequieljsosa/ahk-delivery-infra).

- **Stack:** Java 21, Spring Boot 4.1, Spring Data Redis
- **Datos:** Redis (hash `courier:{id}` y sets `couriers:all` / `couriers:available`)
- **Imagen:** `ezequieljsosa/ahk-delivery-courier-svc`

## API

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/couriers` | Lista los repartidores |
| GET | `/couriers/{id}` | Un repartidor (404 si no existe) |
| POST | `/couriers` | Registra un repartidor |
| PUT | `/couriers/{id}/status` | Cambia el estado (`AVAILABLE` o `BUSY`) |
| POST | `/couriers/assign` | Asigna el libre más cercano a `{lat, lon}` (404 si no hay) |

## Ejecutar localmente

Dependencias: Redis: `docker run -d -p 6379:6379 redis:7`.

Configuración (variables de entorno): `REDIS_HOST` (default `localhost`).

```bash
./mvnw spring-boot:run
```

El servicio escucha en el puerto **8083**.

Imagen de Docker:

```bash
docker build -t ezequieljsosa/ahk-delivery-courier-svc .
```

## Specs

Las features están descritas en [`specs/`](specs/README.md) (desarrollo guiado por specs). Las marcadas *Propuesto* son tareas para los alumnos.

## Calidad de código

```bash
pre-commit install                                  # una sola vez: los hooks corren en cada commit
pre-commit run --all-files                          # formato + análisis estático
mvn checkstyle:check pmd:check spotbugs:check       # solo el análisis estático
```

Necesita un JDK 21 declarado en `~/.m2/toolchains.xml` (plugin de toolchains).

## Licencia

[MIT](LICENSE)
