# 001 · Registrar un repartidor

- **Estado:** Implementado
- **Servicio:** courier-svc
- **Requisitos de sistema:** RS-03, RS-10

## Historia de usuario

Como administrador, quiero dar de alta repartidores, para que se les puedan asignar pedidos.

## Requisitos funcionales

- **RF-1** `POST /couriers` con `name`, `lat` y `lon` crea un repartidor con `id` corto generado (8 caracteres) y estado `AVAILABLE`; responde 201.
- **RF-2** Al arrancar, si no hay repartidores, se crean tres de ejemplo (Lucía, Martín y Sofía).

## Escenarios de aceptación

1. **Dado** un body válido, **cuando** se hace `POST /couriers`, **entonces** responde 201 con `status = AVAILABLE` y el repartidor aparece en `GET /couriers`.
2. **Dado** Redis vacío, **cuando** arranca el servicio, **entonces** hay 3 repartidores disponibles.

## Modelo en Redis

| Clave | Tipo | Contenido |
|---|---|---|
| `courier:{id}` | hash | `name`, `status`, `lat`, `lon` |
| `couriers:all` | set | ids de todos los repartidores |
| `couriers:available` | set | ids de los repartidores libres |

## Fuera de alcance / notas

- No se valida el body (nombre vacío, coordenadas fuera de rango).
