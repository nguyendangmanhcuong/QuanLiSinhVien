package com.ute.studentmvvm.network

import com.google.gson.annotations.SerializedName
import com.ute.studentmvvm.model.Student

data class StudentDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)

// Chuyển DTO từ mạng thành Entity để lưu vào Room
fun StudentDto.toEntity() = Student(
    fullName = name,
    studentCode = "CLOUD%03d".format(id),
    email = email,
    gpa = 3.0 + (id % 10) / 10.0,
    major = "Công Nghệ Thông Tin"
)
