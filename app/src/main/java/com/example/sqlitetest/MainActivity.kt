package com.example.sqlitetest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog

/**
 * Test
 */
class MainActivity : AppCompatActivity() {

    // Object to interact with the DB
    var dbHelper: DBHelper? = null

    // UI elements
    private lateinit var tv1: TextView
    private lateinit var tv2: TextView
    private lateinit var tv3: TextView
    private lateinit var tv4: TextView
    private lateinit var et1: EditText
    private lateinit var et2: EditText
    private lateinit var et3: EditText
    private lateinit var et4: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var btnInsert: Button
    private lateinit var btnSelect: Button
    private lateinit var btnDelete: Button

    private lateinit var ctx: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Gets the IDs from the layout
        getIDS()

        // Instantiate the object
        dbHelper = DBHelper(this@MainActivity)

        // Sets the buttons' listener's
        btnInsert.setOnClickListener { insertStudent() }

        btnSelect.setOnClickListener { selectStudents() }

        btnDelete.setOnClickListener { deleteStudent() }
    }

    private fun deleteStudent() {
        if (et4.text.isNotEmpty()) {
            val rowsDeleted = dbHelper?.delete(et4.text.toString().toLong())
            Log.d(TAG, "deleteStudent: Rows deleted: $rowsDeleted")

            rowsDeleted?.let {
                val msg = if (rowsDeleted > 0) {
                    "$rowsDeleted students have been deleted"
                } else {
                    "There was any student with that ID"
                }

                showToast(msg)
            }

            et4.text.clear()
        } else {
            showToast("ID field empty")
        }
    }

    private fun selectStudents() {
        dbHelper?.select().apply {
            if (this!!.isNotEmpty()) {
                showDialog(this)
                Log.d(TAG, "selectStudents: List Size: ${this.size}")
                forEach { s ->
                    Log.d(TAG, s.toString())
                }
            } else {
                showToast("There are not any students stored")
                Log.d(TAG, "selectStudents: 0 students")
            }
        }
    }

    private fun insertStudent() {
        if (et1.text.toString().isNotEmpty() &&
            et2.text.toString().isNotEmpty() &&
            et3.text.toString().isNotEmpty()
        ) {

            // Adds the Student to the DB
            val id = dbHelper?.addAlumn(
                et1.text.toString(),
                et2.text.toString(),
                et3.text.toString(),
                checkBox.isChecked
            )
            id?.let {
                if (it >= 0) {
                    Log.d(TAG, "insertStudent: Student added with ID $id")
                    showToast("Added successfully")

                    et1.text.clear()
                    et2.text.clear()
                    et3.text.clear()
                    checkBox.isChecked = false
                }
            }
        } else {
            showToast("There are empty fields")
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showDialog(students: List<Student>) {
        var studentsAsString = "# of Students: ${students.size}\n\n"
        students.forEach {
            studentsAsString += "${it.toStringShort()}\n"
        }

        AlertDialog.Builder(ctx)
            .setTitle("List of Students")
            .setMessage(studentsAsString)
            .setPositiveButton(android.R.string.ok, null)
            .create()
            .show()

    }

    private fun getIDS() {
        ctx = this@MainActivity
        tv1 = findViewById(R.id.tv1)
        tv2 = findViewById(R.id.tv2)
        tv3 = findViewById(R.id.tv3)
        tv4 = findViewById(R.id.tv4)
        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        et3 = findViewById(R.id.et3)
        et4 = findViewById(R.id.et4)
        checkBox = findViewById(R.id.cb)
        btnInsert = findViewById(R.id.btnInsert)
        btnSelect = findViewById(R.id.btnSelect)
        btnDelete = findViewById(R.id.btnDelete)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}