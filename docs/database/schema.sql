-- Petagram - modelo de persistencia de las últimas 5 mascotas con rating

CREATE TABLE mascota (
    id INTEGER NOT NULL UNIQUE,
    nombre TEXT NOT NULL,
    emoji TEXT NOT NULL,
    colorRes INTEGER NOT NULL,
    rating INTEGER NOT NULL,
    ratedByUser INTEGER NOT NULL
);

-- El id diferencia cada mascota.
-- El rowid interno de SQLite se usa solamente para ordenar por recencia.
SELECT
    id,
    nombre,
    emoji,
    colorRes,
    rating,
    ratedByUser
FROM mascota
ORDER BY rowid DESC
LIMIT 5;

-- Después de insertar una mascota calificada, se eliminan las más antiguas.
DELETE FROM mascota
WHERE rowid NOT IN (
    SELECT rowid
    FROM mascota
    ORDER BY rowid DESC
    LIMIT 5
);
