package com.example.sqlitetest

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import android.util.Log

/**
 * Class made to interact with the DB. Anything related to the DB will go through this class (CRUD,
 * CREATE DATABASE, ...)
 */

class DBHelper(context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Method called when the database is created
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_DB)
        Log.d(TAG, "onCreate: Database created")
    }

    // Method called when a new database version is used
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(
            TAG,
            "onUpgrade() called with: db = $db, oldVersion = $oldVersion, newVersion = $newVersion"
        )

        db.execSQL(DROP_DB)
        onCreate(db)
    }

    // INSERT
    fun addAlumn(name: String, address: String, dob: String, isNew: Boolean) : Long {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(CN_NAME, name)
            put(CN_ADDRESS, address)
            put(CN_DOB, dob)
            put(CN_IS_NEW, if (isNew) 1 else 0)
        }

        val id = db.insert(TABLE_NAME, null, values)

        db.close()

        return id
    }

    // UPDATE (not tested)
    fun update(newValues: Map<String, *>, id: Long) : Int {
        val db = writableDatabase

        val values = ContentValues().apply {
            // Iterates the values to add
            newValues.forEach { (k,v) ->
                // The value is String
                if (newValues[k] is String) {
                    // Adds the column and the value (String)
                    put(k, v as String)
                }
                // The value is Int
                else if ( newValues[k] is Int) {
                    // Adds the column and the value (int)
                    put(k, v as Int)
                }
            }
        }

        // Where
        val selection = "$_ID = ?"
        val selectionArgs = arrayOf("$id")
        // Executes the statement and gets the number of rows updated
        val count = db.update(
            TABLE_NAME,     // UPDATE Alumnos
            values,         // SET x = y
            selection,      // WHERE id =
            selectionArgs   // y
        )

        db.close()

        return count
    }

    // SELECT
    fun select() : List<Student>{
        val list = mutableListOf<Student>()

        val db = readableDatabase

        val c: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        // It has retrieved something
        if (c.moveToFirst()) {
            // Iterates the rows retrieved
            do {
                val id = c.getInt(c.getColumnIndex(_ID))
                val name = c.getString(c.getColumnIndex(CN_NAME))
                val address = c.getString(c.getColumnIndex(CN_ADDRESS))
                val dob = c.getString(c.getColumnIndex(CN_DOB))
                val isNew = c.getInt(c.getColumnIndex(CN_IS_NEW))

                // Adds the student to the list
                list.add(Student(id,
                    name,
                    address,
                    dob,
                    isNew == 1) // 1 = true, 0 = false
                )

            } while(c.moveToNext())
        }

        c.close()
        db.close()

        return list
    }

    // DELETE
    fun delete(id: Long) : Int {
        val db = writableDatabase

        // The ? will be replaced by the arguments in `selectionArgs`
        val selection = "$_ID = ?"
        val selectionArgs = arrayOf("$id")

        val numOfRowsDeleted = db.delete(
            TABLE_NAME,     // DELETE FROM Alumnos WHERE
            selection,      // _id =
            selectionArgs)  // 1

        db.close()

        return numOfRowsDeleted
    }

    // Constants
    companion object {
        // Database info
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MyDatabase.db"

        // Table and Columns name
        const val TABLE_NAME = "Students"
        const val CN_NAME = "name"          // CN = ColumName
        const val CN_ADDRESS = "address"
        const val CN_DOB = "date_of_birth"
        const val CN_IS_NEW = "is_new_alumn"

        // SQLITE DATA TYPES
        //   - Null
        //   - Integer (Int)
        //   - Real (float, double)
        //   - Text (String)
        //   - Blob (??)

        // Queries
        const val CREATE_DB =
            "CREATE TABLE ${TABLE_NAME} (" +
                    "$_ID INTEGER PRIMARY KEY," +
                    "$CN_NAME TEXT," +
                    "$CN_ADDRESS TEXT," +
                    "$CN_DOB TEXT," +
                    "$CN_IS_NEW INTEGER)"

        const val DROP_DB = "DROP TABLE IF EXISTS $TABLE_NAME"

        // LOG TAG
        private const val TAG = "DBHelper"
    }
}