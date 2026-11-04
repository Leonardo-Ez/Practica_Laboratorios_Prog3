# Programación 3 — Práctica de preparación (Lab 10)
## SpotiPUCP: servicios web SOAP y REST en Java consumidos desde C#

**Duración sugerida:** 2 horas · **Puntaje:** 20 puntos + 3 extra

> Práctica de preparación basada en el caso SpotiPUCP del Lab 05. **No es un enunciado oficial.**

---

## Descripción del caso

La plataforma **SpotiPUCP** ya cuenta con su backend en Java implementado hasta la **capa de negocio**: genera
playlists aleatorias para los usuarios a partir del catálogo de canciones y las registra en la base de datos.

Ahora la plataforma desea **exponer esa lógica como servicios web** para que una aplicación desarrollada en
**C# (.NET)** pueda consultar el catálogo, consultar y registrar playlists, generar playlists aleatorias y eliminarlas.

Se le entrega el proyecto **parcialmente implementado**:

```
practica-lab10/
├── sql/spotipucp.sql            ← script de BD (el del Lab 05 + 5 procedimientos nuevos al final)
└── spotipucp/                   ← proyecto Maven multimódulo (paquete base pe.edu.pucp.spotipucp)
    ├── spotipucp-dbmanager      ← DBManager + db.properties   (YA IMPLEMENTADO)
    ├── spotipucp-modelo         ← Usuario, Cancion, Playlist, PlaylistCancion (YA IMPLEMENTADO)
    ├── spotipucp-accesodatos    ← DAOs + TransactionsManager  (YA IMPLEMENTADO)
    ├── spotipucp-negocio        ← UsuarioBL, CancionBL, PlaylistBL + excepciones (YA IMPLEMENTADO)
    └── spotipucp-app            ← Programa de consola para probar la BL (YA IMPLEMENTADO)
```

**Antes de empezar:**

1. Ejecute `sql/spotipucp.sql` en MySQL.
2. Coloque su contraseña en `spotipucp-dbmanager/src/main/resources/db.properties`.
3. Ejecute `spotipucp-app` → `Programa.main`. Debe imprimir una playlist de 10 canciones por cada usuario.
   Si esto no funciona, **no avance**: los servicios web no funcionarán.

### Capa de negocio disponible (no debe modificarla)

| Interfaz | Operación | Excepciones relevantes |
|---|---|---|
| `UsuarioBL` | `List<Usuario> findAll()` | `BLException` |
| | `Usuario findById(int id)` | `NoEncontradoException` |
| `CancionBL` | `List<Cancion> findAll()` | `BLException` |
| | `Cancion findById(int id)` | `NoEncontradoException` |
| | `List<Cancion> seleccionarCancionesAleatorias()` | `BLException` |
| `PlaylistBL` | `List<Playlist> findAll()` | `BLException` |
| | `Playlist findById(int id)` | `NoEncontradoException` |
| | `List<Playlist> findByUsuario(int idUsuario)` | `NoEncontradoException` (usuario) |
| | `Playlist crearPlaylistConCanciones(int idUsuario, String nombre, List<Integer> idsCanciones)` | `ValidacionException` |
| | `List<Playlist> generarPlaylistsParaTodos()` | `BLException` |
| | `void delete(int id)` | `NoEncontradoException` |

`NoEncontradoException` y `ValidacionException` heredan de `BLException`.

### Modelo disponible

- `Usuario(id, codigo, nombre, correo)`
- `Cancion(id, titulo, artista, genero, duracionSegundos)`
- `Playlist(id, usuario: Usuario, nombre, fechaCreacion: LocalDate, canciones: List<PlaylistCancion>)` + `getDuracionTotalSegundos()`
- `PlaylistCancion(id, cancion: Cancion, orden)`

> ⚠️ JAX-WS (JAXB) **no** sabe serializar `LocalDate` sin un adaptador. Por eso **no exponga el modelo
> directamente**: use **DTOs** con la fecha como `String` (`yyyy-MM-dd`), tanto en SOAP como en REST, por uniformidad.

---

## Pregunta 01 — Servicio web SOAP en Java (6 puntos)

Cree el módulo **`spotipucp-ws`** (packaging `war`) dentro del proyecto multimódulo y regístrelo en el `pom.xml` padre.

1. **(1 pt) Configuración del módulo:**
   - dependencia a `spotipucp-negocio`;
   - dependencia `jakarta.xml.ws-api` con `scope provided`;
   - `finalName` = `spotipucp-ws`.
2. **(1.5 pt) DTOs y mappers** en los paquetes `pe.edu.pucp.spotipucp.ws.dto` y `...ws.mapper`:
   - DTOs: `UsuarioDTO`, `CancionDTO`, `PlaylistCancionDTO` y `PlaylistMapper`;
   - `PlaylistMapper` incluye `fechaCreacion` como `String`, `duracionTotalSegundos` y la lista de canciones **inicializada**.
3. **(3.5 pt) Servicios** con `@WebService(targetNamespace = "http://ws.spotipucp.pucp.edu.pe/")`:

| Servicio | Operación (`operationName`) | Parámetros | Retorno |
|---|---|---|---|
| `UsuarioWS` | `listarUsuarios` | — | `List<UsuarioDTO>` |
| `CancionWS` | `listarCanciones` | — | `List<CancionDTO>` |
| | `seleccionarCancionesAleatorias` | — | `List<CancionDTO>` |
| `PlaylistWS` | `listarPlaylists` | — | `List<PlaylistDTO>` |
| | `obtenerPlaylist` | `id` | `PlaylistMapper` |
| | `listarPlaylistsPorUsuario` | `idUsuario` | `List<PlaylistDTO>` |
| | `registrarPlaylist` | `idUsuario`, `nombre`, `idsCanciones` (`List<Integer>`) | `int` (id generado) |
| | `generarPlaylists` | — | `int` (cantidad generada) |
| | `eliminarPlaylist` | `id` | `void` |

Todos los parámetros deben llevar `@WebParam(name = "...")`. Los errores de negocio se propagan con
`throws BLException`, de modo que lleguen al cliente como **SOAP Fault**.

Despliegue en GlassFish y verifique el WSDL en `http://localhost:8080/spotipucp-ws/PlaylistWS?wsdl`.

---

## Pregunta 02 — Servicio web REST en Java (6 puntos)

Cree el módulo **`spotipucp-rest`** (packaging `war`) y regístrelo en el `pom.xml` padre.

1. **(1 pt) Configuración:**
   - dependencias `jakarta.ws.rs-api`, `jakarta.enterprise.cdi-api` y `jakarta.servlet-api`, todas `provided`;
   - archivo `META-INF/beans.xml`;
   - clase `SpotiPucpApplication` con `@ApplicationPath("/api/v1")`.
2. **(0.5 pt) DTOs y mappers propios** en `pe.edu.pucp.spotipucp.rest.dto` / `...rest.mapper`. Puede copiar los de
   la P1, pero el módulo REST **no debe depender** del módulo SOAP.
3. **(4.5 pt) Recursos** (`@Produces` / `@Consumes` `application/json`):

| Método | Ruta | Descripción | Respuestas esperadas |
|---|---|---|---|
| GET | `/usuarios` | Lista usuarios | 200 |
| GET | `/usuarios/{id}/playlists` | Playlists de un usuario | 200 · 404 si el usuario no existe |
| GET | `/canciones` | Catálogo completo | 200 |
| GET | `/canciones?aleatorias=true` | 10 canciones al azar | 200 |
| GET | `/playlists` | Lista playlists con detalle | 200 |
| GET | `/playlists/{id}` | Una playlist | 200 · 404 |
| POST | `/playlists` | Registra una playlist (ver body) | **201 + header `Location`** + playlist creada · 400 |
| POST | `/playlists/generar` | Genera una playlist aleatoria por usuario | 201 + lista generada |
| DELETE | `/playlists/{id}` | Elimina una playlist | **204** · 404 |

Body del `POST /playlists`:

```json
{ "idUsuario": 3, "nombre": "Para estudiar", "idsCanciones": [5, 11, 15, 25] }
```

Para este body, cree un DTO de entrada (`RegistroPlaylistDTO`).

Cualquier otra `BLException` debe responder **500**. Todo error debe devolver un cuerpo
`{"error": "<mensaje>"}`.

Pruebe todos los endpoints con Postman, IntelliJ HTTP Client o `curl` antes de pasar a C#.

---

## Pregunta 03 — Consumo SOAP desde C# (4 puntos)

Cree una solución **`SpotiPUCP.Front`** con una aplicación de **consola** (.NET 10) llamada `SpotiPUCP.Cliente`.

1. **(0.5 pt) Modelos propios en C#:**
   - `UsuarioVM`, `CancionVM`, `PlaylistVM` (con `List<PlaylistCancionVM>`) y `PlaylistCancionVM`;
   - convenciones de C#: propiedades en PascalCase, `DateOnly FechaCreacion`.
2. **(0.5 pt) Contrato** en una interfaz `ISpotiService`:
   ```csharp
   List<UsuarioVM> ListarUsuarios();
   List<CancionVM> ListarCanciones();
   List<PlaylistVM> ListarPlaylists();
   PlaylistVM? ObtenerPlaylist(int id);            // null si no existe
   int RegistrarPlaylist(int idUsuario, string nombre, List<int> idsCanciones);
   int GenerarPlaylists();
   void EliminarPlaylist(int id);
   ```
   Si el servicio responde con un error, la implementación lanza `ServicioException` con el mensaje que envió el servidor.
3. **(3 pt) `SpotiSoapService : ISpotiService`:**
   - agregue las referencias WCF (*Connected Services*) a `UsuarioWS`, `CancionWS` y `PlaylistWS`;
   - mapee los DTO generados a sus VM;
   - convierta cada `FaultException` en `ServicioException`.

---

## Pregunta 04 — Consumo REST desde C# y programa principal (4 puntos)

1. **(2.5 pt) `SpotiRestService : ISpotiService`:**
   - use `HttpClient` y `System.Net.Http.Json` (`GetFromJsonAsync`, `PostAsJsonAsync`, `DeleteAsync`);
   - lea la URL base de `appsettings.json` o de una constante;
   - `ObtenerPlaylist` devuelve `null` ante un **404**;
   - cualquier respuesta que no sea 2xx lanza `ServicioException` con el `error` del cuerpo JSON.
2. **(1.5 pt) `Program.cs`:** al iniciar, pregunte `¿Usar SOAP (1) o REST (2)?`, cree la implementación
   correspondiente **como `ISpotiService`** y ejecute la secuencia siguiente.
   1. Mostrar la cantidad de usuarios y de canciones del catálogo.
   2. Registrar una playlist `"Mi playlist C#"` para el usuario 1 con las canciones `[1, 2, 3, 4, 5]`
      y mostrar el id generado.
   3. Mostrar esa playlist con `ObtenerPlaylist`: nombre, usuario, fecha, duración total en `mm:ss`
      y canciones en orden.
   4. Intentar registrar una playlist con canciones repetidas `[1, 1, 2]`, capturar `ServicioException`
      y mostrar el mensaje.
   5. Eliminar la playlist del paso 2 y verificar que `ObtenerPlaylist` ahora devuelve `null`.

   La salida debe ser **idéntica** con SOAP y con REST. Eso demuestra que la aplicación depende del contrato y
   no de la tecnología.

---

## Puntos extra

- **(+1)** En REST, agregue `GET /playlists?usuario={id}`. Este filtro reutiliza `findByUsuario` y en C# se usa con
  un nuevo método `ListarPlaylistsPorUsuario`.
- **(+1)** En C#, agregue a `ISpotiService` el método `GenerarPlaylists()` e imprima un resumen: cuántas playlists
  se generaron y la de mayor duración total.
- **(+1)** En REST, el `POST /playlists` devuelve **409 Conflict** si el usuario ya tiene una playlist con el mismo
  nombre. Agréguelo como validación en el recurso **sin modificar la BL**, usando `findByUsuario`.

---

## Checklist de errores frecuentes

- [ ] Hizo `mvn install` desde el **pom padre** antes de desplegar el `war` (si no, GlassFish no encuentra la BL).
- [ ] El `db.properties` está en `src/main/resources` del dbmanager y viaja dentro del jar.
- [ ] Los DTO tienen constructor vacío y getters/setters. Sin ellos, los campos llegan vacíos o vienen en `null`.
- [ ] Si cambió algo en un `WebService`, redespliegue y **actualice la referencia** en Visual Studio.
- [ ] La URL base REST termina en `/` (`.../api/v1/`) y las rutas relativas **no** empiezan con `/`.
- [ ] El `POST` usa `uriInfo.getAbsolutePathBuilder().path(id)` para construir el `Location`.
- [ ] El orden de los `catch` va del más específico al más general: `NoEncontrado`/`Validacion` → `BLException`.
