# 005 · Desconectar repartidores inactivos

- **Estado:** Propuesto
- **Servicio:** courier-svc
- **Requisitos de sistema:** RS-03

## Historia de usuario

Como operador, quiero que un repartidor que no reporta actividad deje de ser asignable, para no mandar pedidos a alguien desconectado.

## Requisitos funcionales

- **RF-1** `PUT /couriers/{id}/heartbeat` renueva la presencia del repartidor con un TTL de 60 segundos en Redis.
- **RF-2** Un repartidor sin presencia vigente no se considera en la asignación, aunque su estado sea `AVAILABLE`.
- **RF-3** `GET /couriers` informa si cada repartidor está conectado.

## Escenarios de aceptación

1. **Dado** un repartidor sin heartbeat hace más de 60 s, **cuando** se pide una asignación, **entonces** no se lo elige.
2. **Dado** que envía un heartbeat, **cuando** se pide una asignación, **entonces** vuelve a poder ser elegido.

## Fuera de alcance / notas

- Pista: `expire` sobre una clave `courier:{id}:alive`.
