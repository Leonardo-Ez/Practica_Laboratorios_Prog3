# Practica_Laboratorios_Prog3

Prácticas de laboratorio del curso Programación 3.

## Laboratorio 10 – SpotiPUCP

Proyecto Maven multimódulo (`practica-lab10/spotipucp`) con arquitectura en capas.

Las capas hasta la de negocio fueron construidas con [Claude Code](https://claude.com/claude-code), para que yo pudiera enfocarme directamente en implementar los servicios web:

| Módulo | Capa | Autor |
|---|---|---|
| `spotipucp-modelo` | Entidades del dominio | Claude Code |
| `spotipucp-dbmanager` | Conexión a la base de datos (MySQL) | Claude Code |
| `spotipucp-accesodatos` | DAOs y manejo de transacciones | Claude Code |
| `spotipucp-negocio` | Lógica de negocio (BL) y validaciones | Claude Code |
| `spotipucp-ws` | Servicios web SOAP, DTOs y mappers | Leonardo :) |

El script de la base de datos está en `practica-lab10/sql/`.

### Configuración

Las credenciales de la base de datos no están en el repositorio. Antes de ejecutar, crea el archivo
`practica-lab10/spotipucp/spotipucp-dbmanager/src/main/resources/db.properties`:

```properties
servidor=<host de MySQL>
puerto=3306
esquema=spotipucp
usuario=<usuario>
password=<contraseña>
```
