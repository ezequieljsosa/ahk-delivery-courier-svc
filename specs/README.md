# Specs (Spec-Driven Development)

Cada carpeta `NNN-nombre/` describe **una feature** del servicio: qué tiene que hacer y cómo
se verifica, antes de hablar de código. Los requisitos que cruzan varios servicios están en el
repositorio de infraestructura ([`docs/requirements.md` de ahk-delivery-infra](https://github.com/ezequieljsosa/ahk-delivery-infra/blob/main/docs/requirements.md)); acá se referencian como `RS-xx`.

## Estado

| # | Feature | Estado |
|---|---|---|
| 001 | [Registrar un repartidor](001-registrar-repartidor/spec.md) | Implementado |
| 002 | [Consultar repartidores](002-consultar-repartidores/spec.md) | Implementado |
| 003 | [Cambiar el estado de un repartidor](003-cambiar-estado/spec.md) | Implementado |
| 004 | [Asignar el repartidor libre más cercano](004-asignar-repartidor-mas-cercano/spec.md) | Implementado |
| 005 | [Desconectar repartidores inactivos](005-ttl-disponibilidad/spec.md) | Propuesto |
| 006 | [Asignación atómica](006-asignacion-atomica/spec.md) | Propuesto |

*Implementado* = el comportamiento ya existe y el spec lo describe. *Propuesto* = todavía no existe:
es una tarea para implementar siguiendo el flujo de abajo.

## Flujo para cambiar o agregar una feature

1. **Spec**: crear `specs/NNN-nombre/spec.md` (o editar el existente) con el formato de abajo.
   Si cambia un contrato entre servicios, actualizar también [`docs/requirements.md` de ahk-delivery-infra](https://github.com/ezequieljsosa/ahk-delivery-infra/blob/main/docs/requirements.md).
2. **Plan**: crear `plan.md` en la misma carpeta con qué clases/archivos se tocan y cómo se prueba.
3. **Tareas**: crear `tasks.md` con pasos chicos y verificables (checklist).
4. **Implementar** siguiendo las tareas; cada escenario de aceptación del spec debe tener su test.
5. Marcar el spec como *Implementado* y correr los chequeos de calidad (ver el [README de ahk-delivery-infra](https://github.com/ezequieljsosa/ahk-delivery-infra#readme)).

## Formato de un spec

```markdown
# NNN · Título

- **Estado:** Propuesto | Implementado
- **Requisitos de sistema:** RS-xx

## Historia de usuario
Como <rol>, quiero <capacidad>, para <beneficio>.

## Requisitos funcionales
- **RF-1** El sistema debe ...

## Escenarios de aceptación
1. **Dado** ... **cuando** ... **entonces** ...

## Fuera de alcance / notas
```
