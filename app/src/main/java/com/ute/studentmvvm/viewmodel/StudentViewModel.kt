package com.ute.studentmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ute.studentmvvm.model.Student
import com.ute.studentmvvm.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {

    // Chuyển Flow sang LiveData trên Dispatchers.IO
    val allStudents: LiveData<List<Student>> = repository.allStudents.asLiveData(Dispatchers.IO)

    private val _message = MutableSharedFlow<String>(extraBufferCapacity = 8)
    val message: SharedFlow<String> = _message.asSharedFlow()

    fun insert(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.insert(student)
            _message.tryEmit("Đã thêm sinh viên: ${student.fullName}")
        } catch (e: Exception) {
            _message.tryEmit("Lỗi khi thêm: ${e.message}")
        }
    }

    fun delete(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.delete(student)
            _message.tryEmit("Đã xóa sinh viên: ${student.fullName}")
        } catch (e: Exception) {
            _message.tryEmit("Lỗi khi xóa: ${e.message}")
        }
    }

    fun syncFromCloud() = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.syncFromCloud()
        if (result.isSuccess) {
            _message.tryEmit("Đã đồng bộ ${result.getOrNull()} sinh viên từ máy chủ!")
        } else {
            _message.tryEmit("Chưa thể kết nối máy chủ Cloud, sử dụng dữ liệu cục bộ.")
        }
    }
}