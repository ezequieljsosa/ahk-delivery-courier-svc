# 004 · Asignar el repartidor libre más cercano

- **Estado:** Implementado
- **Servicio:** courier-svc
- **Requisitos de sistema:** RS-03

## Historia de usuario

Como servicio de pedidos, quiero que me asignen el repartidor libre más cercano a un punto, para minimizar el tiempo de retiro.

## Requisitos funcionales

- **RF-1** `POST /couriers/assign` con `{lat, lon}` elige, entre los repartidores `AVAILABLE`, el de menor distancia (haversine) al punto.
- **RF-2** El elegido pasa a `BUSY` y se devuelve completo (200).
- **RF-3** Si no hay ningún repartidor libre, responde 404.

## Escenarios de aceptación

1. **Dado** tres repartidores libres, **cuando** se asigna cerca de uno de ellos, **entonces** responde con ese repartidor y queda `BUSY`.
2. **Dado** que todos están `BUSY`, **cuando** se pide una asignación, **entonces** responde 404.

## Fuera de alcance / notas

- Dos asignaciones simultáneas pueden elegir al mismo repartidor (leer y marcar no es atómico). Ver 006.
