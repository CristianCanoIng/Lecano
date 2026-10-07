package com.example.lecano

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MascotaDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_MASCOTA (
                $COL_ID INTEGER NOT NULL UNIQUE,
                $COL_NOMBRE TEXT NOT NULL,
                $COL_EMOJI TEXT NOT NULL,
                $COL_COLOR_RES INTEGER NOT NULL,
                $COL_RATING INTEGER NOT NULL,
                $COL_RATED_BY_USER INTEGER NOT NULL
            )
            """.trimIndent()
        )

        // Se insertan en orden inverso para conservar el orden visual
        // inicial de la actividad anterior al consultar DESC por rowid.
        MascotaData.favoritePets()
            .asReversed()
            .forEach { mascota ->
                insertMascota(db, mascota)
            }

        trimToFive(db)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MASCOTA")
        onCreate(db)
    }

    fun saveRatedPet(mascota: Mascota) {
        val db = writableDatabase

        db.beginTransaction()
        try {
            // Al eliminar e insertar nuevamente, la mascota recibe un nuevo
            // rowid interno y pasa a ser la más reciente sin duplicar su id.
            db.delete(
                TABLE_MASCOTA,
                "$COL_ID = ?",
                arrayOf(mascota.id.toString())
            )

            insertMascota(db, mascota)
            trimToFive(db)

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun getLastFivePets(): MutableList<Mascota> {
        val mascotas = mutableListOf<Mascota>()

        readableDatabase.query(
            TABLE_MASCOTA,
            arrayOf(
                COL_ID,
                COL_NOMBRE,
                COL_EMOJI,
                COL_COLOR_RES,
                COL_RATING,
                COL_RATED_BY_USER
            ),
            null,
            null,
            null,
            null,
            "rowid DESC",
            "5"
        ).use { cursor ->
            val idIndex = cursor.getColumnIndexOrThrow(COL_ID)
            val nombreIndex = cursor.getColumnIndexOrThrow(COL_NOMBRE)
            val emojiIndex = cursor.getColumnIndexOrThrow(COL_EMOJI)
            val colorIndex = cursor.getColumnIndexOrThrow(COL_COLOR_RES)
            val ratingIndex = cursor.getColumnIndexOrThrow(COL_RATING)
            val ratedIndex = cursor.getColumnIndexOrThrow(COL_RATED_BY_USER)

            while (cursor.moveToNext()) {
                mascotas += Mascota(
                    id = cursor.getInt(idIndex),
                    nombre = cursor.getString(nombreIndex),
                    emoji = cursor.getString(emojiIndex),
                    colorRes = cursor.getInt(colorIndex),
                    rating = cursor.getInt(ratingIndex),
                    ratedByUser = cursor.getInt(ratedIndex) == 1
                )
            }
        }

        return mascotas
    }

    fun getSavedPetsById(): Map<Int, Mascota> =
        getLastFivePets().associateBy { it.id }

    private fun insertMascota(
        db: SQLiteDatabase,
        mascota: Mascota
    ) {
        val values = ContentValues().apply {
            put(COL_ID, mascota.id)
            put(COL_NOMBRE, mascota.nombre)
            put(COL_EMOJI, mascota.emoji)
            put(COL_COLOR_RES, mascota.colorRes)
            put(COL_RATING, mascota.rating)
            put(COL_RATED_BY_USER, if (mascota.ratedByUser) 1 else 0)
        }

        db.insertOrThrow(TABLE_MASCOTA, null, values)
    }

    private fun trimToFive(db: SQLiteDatabase) {
        db.execSQL(
            """
            DELETE FROM $TABLE_MASCOTA
            WHERE rowid NOT IN (
                SELECT rowid
                FROM $TABLE_MASCOTA
                ORDER BY rowid DESC
                LIMIT 5
            )
            """.trimIndent()
        )
    }

    companion object {
        const val DATABASE_NAME = "petagram.db"
        const val DATABASE_VERSION = 1

        const val TABLE_MASCOTA = "mascota"
        const val COL_ID = "id"
        const val COL_NOMBRE = "nombre"
        const val COL_EMOJI = "emoji"
        const val COL_COLOR_RES = "colorRes"
        const val COL_RATING = "rating"
        const val COL_RATED_BY_USER = "ratedByUser"
    }
}
