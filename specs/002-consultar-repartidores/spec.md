# 002 · Consultar repartidores

- **Estado:** Implementado
- **Servicio:** courier-svc
- **Requisitos de sistema:** RS-03

## Historia de usuario

Como operador, quiero ver los repartidores y su estado, para saber quién está libre.

## Requisitos funcionales

- **RF-1** `GET /couriers` lista todos los repartidores.
- **RF-2** `GET /couriers/{id}` devuelve uno; 404 si no existe.

## Escenarios de aceptación

1. **Dado** un repartidor existente, **cuando** se pide por id, **entonces** responde 200 con `id`, `name`, `status`, `lat` y `lon`.
2. **Dado** un id inexistente, **cuando** se pide por id, **entonces** responde 404.
