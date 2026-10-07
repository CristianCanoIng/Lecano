# Lecano · Petagram — Persistencia de últimas 5 mascotas

Esta rama continúa el proyecto anterior de Petagram y agrega **persistencia local con SQLite** para que la pantalla de las últimas 5 mascotas deje de depender de datos hardcodeados.

## Rama de esta actividad

```text
actividad-persistencia-mascotas
```

Parte de `actividad-menus-fragments`, por lo que las actividades anteriores permanecen separadas.

## Objetivo

Cada vez que el usuario da rating a una mascota:

- Se incrementa su rating.
- Se guarda la **entidad completa** en SQLite.
- El `id` único evita confundir una mascota con otra.
- La mascota calificada pasa a ser la más reciente.
- La base de datos conserva únicamente las **últimas 5 mascotas con rating**.
- La pantalla de favoritas obtiene sus datos directamente de SQLite.

## Entidad Mascota

```kotlin
data class Mascota(
    val id: Int,
    val nombre: String,
    val emoji: String,
    val colorRes: Int,
    var rating: Int,
    var ratedByUser: Boolean = false
)
```

El campo `id` funciona como identificador único de la mascota.

## Modelo de base de datos

Base de datos:

```text
petagram.db
```

Tabla:

```text
mascota
```

Modelo:

```text
┌─────────────────────────────┐
│           mascota           │
├─────────────────────────────┤
│ UQ  id          INTEGER     │
│     nombre      TEXT        │
│     emoji       TEXT        │
│     colorRes    INTEGER     │
│     rating      INTEGER     │
│     ratedByUser INTEGER     │
└─────────────────────────────┘
```

La tabla conserva los mismos campos del POJO `Mascota`. El campo `id` se declara `UNIQUE` y los booleanos se representan como `0` o `1`.

SQLite mantiene internamente un `rowid` que se usa únicamente para saber cuál fue el registro guardado más recientemente. No forma parte del modelo declarado.

Documentación del modelo:

```text
docs/database/modelo-base-datos.md
docs/database/schema.sql
```

## Persistencia

La clase principal es:

```text
MascotaDatabaseHelper.kt
```

Está implementada con `SQLiteOpenHelper` y ofrece dos operaciones principales:

```kotlin
saveRatedPet(mascota)
getLastFivePets()
```

### Al dar rating

```text
Usuario pulsa hueso
       ↓
MascotaAdapter incrementa rating
       ↓
PetsFragment recibe callback
       ↓
MascotaDatabaseHelper.saveRatedPet(...)
       ↓
DELETE del registro anterior con el mismo id
       ↓
INSERT de la entidad completa
       ↓
Se eliminan registros antiguos hasta conservar 5
```

El borrado previo evita duplicados y permite que una mascota vuelva a posicionarse como la más reciente.

### Pantalla de últimas 5 mascotas

`FavoritesActivity` ya no utiliza la lista dummy. Ahora ejecuta:

```kotlin
databaseHelper.getLastFivePets()
```

y muestra el resultado usando el mismo `MascotaAdapter`.

## Persistencia entre ejecuciones

Al volver a abrir la aplicación, `PetsFragment` consulta los registros guardados y recupera los ratings persistidos de las mascotas presentes entre las últimas cinco.

## Estructura agregada/modificada

```text
app/src/main/java/com/example/lecano/
├── Mascota.kt
├── MascotaData.kt
├── MascotaAdapter.kt
├── MascotaDatabaseHelper.kt   # SQLite
├── PetsFragment.kt            # Guarda rating
└── FavoritesActivity.kt       # Lee últimas 5
```

## Criterios cubiertos

- ✅ Identificador único por mascota
- ✅ Tabla SQLite `mascota`
- ✅ Tabla equivalente al POJO
- ✅ Persistencia de la entidad completa al dar rating
- ✅ Últimas 5 mascotas almacenadas
- ✅ Eliminación automática de registros antiguos
- ✅ Favoritas obtenidas desde base de datos
- ✅ Persistencia local entre ejecuciones
- ✅ Modelo de base de datos documentado
- ✅ Se conservan Menús, Fragments, ViewPager2 y JavaMail de la actividad anterior

## Evidencias

Las capturas de esta actividad deberán mostrar, como mínimo:

1. Lista principal antes/después de dar rating.
2. Pantalla de las últimas 5 mascotas actualizada.
3. Cambio de una de las mascotas de la lista tras dar un nuevo rating.

Súbelas a:

```text
docs/screenshots/
```

Cuando estén subidas, el README puede enlazarlas directamente.

## Descargar esta actividad

```bash
git fetch origin
git switch actividad-persistencia-mascotas
git pull origin actividad-persistencia-mascotas
```

## Repositorio

**CristianCanoIng/Lecano**
