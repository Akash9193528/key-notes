package ap.akash.keynotes.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ap.akash.keynotes.model.note

class DatabaseHelper(context : Context)  : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION ) {


    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "all_notes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createtablequery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT , $COLUMN_DATE TEXT )"
        db?.execSQL(createtablequery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newversion: Int) {
        val drop_table_query = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(drop_table_query)
        onCreate(db)

    }


    fun insertnote(note: note) {
        val db = writableDatabase
        val value = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_DATE, note.date)
        }
        db.insert(TABLE_NAME, null, value)
        db.close()
    }

    fun getallnote(): List<note> {
        val notelist = mutableListOf<note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title  = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val note = note(id,title,description,date)
            notelist.add(note)

        }
        cursor.close()
        db.close()
        return notelist


      }

    fun updatenote(note: note){
        val db = writableDatabase
        val value = ContentValues().apply{
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_DATE, note.date)
        }

        val whereclause = "$COLUMN_ID = ?"
        val whereargs = arrayOf(note.id.toString())
        db.update(TABLE_NAME, value, whereclause, whereargs)
        db.close()

    }

    fun getnotebyId (noteid : Int): note {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteid"
        val cursor = db.rawQuery(query, null)
         cursor.moveToFirst()
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))

            cursor.close()
            db.close()

            return note(id, title, content, date)

        }



    fun deletenote(noteid :Int){
        val db = writableDatabase
        val whereclause = "$COLUMN_ID = ?"
        val whereargs = arrayOf(noteid.toString())
        db.delete(TABLE_NAME, whereclause, whereargs)
        db.close()
    }








}

