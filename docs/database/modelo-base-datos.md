# Modelo de base de datos — Petagram

La persistencia utiliza **SQLite** mediante `SQLiteOpenHelper`.

## Entidad Mascota

El POJO/modelo Kotlin y la tabla SQLite mantienen los mismos campos:

| Campo | Kotlin | SQLite | Restricción |
|---|---|---|---|
| `id` | `Int` | `INTEGER` | `NOT NULL UNIQUE` |
| `nombre` | `String` | `TEXT` | `NOT NULL` |
| `emoji` | `String` | `TEXT` | `NOT NULL` |
| `colorRes` | `Int` | `INTEGER` | `NOT NULL` |
| `rating` | `Int` | `INTEGER` | `NOT NULL` |
| `ratedByUser` | `Boolean` | `INTEGER` | `NOT NULL`, 0/1 |

## Modelo lógico

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

El identificador `id` es único y permite distinguir una mascota de otra.

SQLite mantiene además su `rowid` interno, que **no forma parte del POJO ni de la tabla declarada**, y se utiliza solamente para ordenar los registros por recencia.

## Regla de negocio

Cuando el usuario da rating:

1. Se actualiza la entidad completa en memoria.
2. Si esa mascota ya existe en SQLite, se elimina su registro anterior.
3. Se inserta nuevamente con todos sus datos actuales.
4. La nueva inserción recibe el `rowid` más reciente.
5. SQLite conserva únicamente los **5 registros más recientes**.
6. La pantalla **5 mascotas favoritas** consulta esos registros ordenados por recencia.

De esta manera una mascota ya conocida puede volver a ocupar la primera posición sin duplicar su `id`.

El SQL equivalente se encuentra en [schema.sql](schema.sql).
