# AGENTS.md

Guía para quien trabaje en este repositorio, humano o asistente de IA.

## Qué es

`ahk-delivery-courier-svc`: repartidores y asignación de la maqueta ahk-delivery. Mantiene los repartidores y su disponibilidad en Redis y asigna el repartidor libre más cercano a un punto.
El contexto del sistema completo está en [ahk-delivery-infra](https://github.com/ezequieljsosa/ahk-delivery-infra): `docs/requirements.md` (requisitos y diagramas de secuencia) y `docs/architecture.md` (C4).

## Comandos

- Compilar y correr: `./mvnw spring-boot:run`
- Tests: `./mvnw test` (todavía no hay: cada escenario de aceptación nuevo debe tener su test)
- Calidad: `pre-commit run --all-files` o `mvn checkstyle:check pmd:check spotbugs:check`

## Cómo trabajar: specs primero (SDD)

1. Antes de tocar código, leer el spec de la feature en `specs/NNN-nombre/spec.md`. Si no existe, crearlo (formato en `specs/README.md`).
2. Escribir `plan.md` y `tasks.md` en la carpeta del spec y recién después implementar.
3. Cada escenario de aceptación del spec debe tener su test.
4. Si el cambio altera un contrato entre servicios (REST o eventos), actualizar también los requisitos en [ahk-delivery-infra](https://github.com/ezequieljsosa/ahk-delivery-infra/blob/main/docs/requirements.md) y avisar a los otros repos afectados.
5. Al terminar, marcar el spec como *Implementado*.

## Reglas de este servicio

- Controllers explícitos con `@RestController`; no usar Spring Data REST.
- Redis es el único almacén; no agregar otra base.
- Cada servicio es dueño de su base: nunca acceder a la base de otro servicio.

## Estilo y calidad

- Formato automático con `pre-commit` (google-java-format AOSP): no discutir el formato, dejar que lo aplique el hook.
- `checkstyle.xml` es liviano a propósito (imports, nombres, buenas prácticas básicas, líneas hasta 120). No endurecerlo sin que lo pidan.
- Identificadores en inglés; mensajes de log y documentación en español.
- Sin imports con comodín en código de producción.

## Antes de dar por terminada una tarea

- Correr `pre-commit run --all-files`, dejar que los formatters reescriban archivos y revisar el diff.
- Verificar que el spec refleja lo implementado.
- No commitear secretos ni artefactos generados (`target/`, `*.joblib`, `__pycache__/`).
