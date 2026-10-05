# 006 · Asignación atómica

- **Estado:** Propuesto
- **Servicio:** courier-svc
- **Requisitos de sistema:** RS-03, RS-09

## Historia de usuario

Como servicio de pedidos, quiero que dos pedidos simultáneos nunca reciban el mismo repartidor.

## Requisitos funcionales

- **RF-1** Elegir y marcar `BUSY` ocurre de forma atómica (por ejemplo `SREM` sobre `couriers:available`: solo gana quien logra sacarlo del set).
- **RF-2** Si el candidato fue tomado por otro, se prueba con el siguiente más cercano.

## Escenarios de aceptación

1. **Dado** un único repartidor libre, **cuando** llegan 20 asignaciones concurrentes, **entonces** exactamente una responde 200 y el resto 404.

## Fuera de alcance / notas

- Es una buena tarea para mostrar condiciones de carrera con un test concurrente.
