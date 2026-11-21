package com.ute.studentmvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
/**
 * Entity đại diện cho bảng "students_table" trong SQLite.
 */
@Entity(
    tableName = "students_table",
    indices = [Index(value = ["student_code"], unique = true)]
)
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)