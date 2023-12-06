package com.example.notes_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PaginaDataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "diarioapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "todaspaginas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun inserirPagina(pagina: Convidado){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, pagina.title)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun pegarTodasPaginas(): List<Convidado> {
        val notesList = mutableListOf<Convidado>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))

            val pagina = Convidado(id, title)
            notesList.add(pagina)
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateNote(pagina: Convidado){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, pagina.title)

        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(pagina.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getNoteByID (paginaId: Int): Convidado{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $paginaId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))

        cursor.close()
        db.close()
        return Convidado(id, title)
    }

    fun deleteNote(paginaId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(paginaId.toString())
        db.delete(TABLE_NAME , whereClause, whereArgs)
        db.close()
    }
}