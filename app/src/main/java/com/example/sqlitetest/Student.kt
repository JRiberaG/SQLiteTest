package com.example.sqlitetest

data class Student(
    var id: Int,
    var name: String,
    var address: String,
    var dob: String,
    var isNew: Boolean
) {
    override fun toString(): String {
        return "Student(id=$id, name='$name', address='$address', dob='$dob', isNew=$isNew)"
    }

    fun toStringShort() : String {
        return "- $id, $name, $address, $dob, $isNew"
    }
}