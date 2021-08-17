package com.kiran.retrofitstarterbatch26.ui.repository

import com.kiran.retrofitstarterbatch26.ui.api.MyApiRequest
import com.kiran.retrofitstarterbatch26.ui.api.ServiceBuilder
import com.kiran.retrofitstarterbatch26.ui.api.StudentAPI
import com.kiran.retrofitstarterbatch26.ui.model.Student
import com.kiran.retrofitstarterbatch26.ui.response.AddStudentResponse
import com.kiran.retrofitstarterbatch26.ui.response.DeleteStudentResponse
import com.kiran.retrofitstarterbatch26.ui.response.StudentGetResponse
import okhttp3.MultipartBody

class StudentRepository : MyApiRequest() {
    private val studentApi =
        ServiceBuilder.buildService(StudentAPI::class.java)

    suspend fun getAllStudent(): StudentGetResponse {
        return apiRequest {
            studentApi.getAllStudents(ServiceBuilder.token!!)
        }
    }

    suspend fun deleteStudent(id: String): DeleteStudentResponse {
        return apiRequest {
            studentApi.deleteStudent(ServiceBuilder.token!!, id)
        }
    }

    suspend fun insertStudent(student: Student): AddStudentResponse {
        return apiRequest {
            studentApi.insertStudent(ServiceBuilder.token!!, student)
        }
    }


    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : DeleteStudentResponse {
        return apiRequest {
            studentApi.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }

}