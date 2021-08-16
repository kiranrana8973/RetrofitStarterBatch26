package com.kiran.retrofitstarterbatch26.ui.repository

import com.kiran.retrofitstarterbatch26.ui.api.MyApiRequest
import com.kiran.retrofitstarterbatch26.ui.api.ServiceBuilder
import com.kiran.retrofitstarterbatch26.ui.api.StudentAPI
import com.kiran.retrofitstarterbatch26.ui.response.DeleteStudentResponse
import com.kiran.retrofitstarterbatch26.ui.response.StudentGetResponse

class StudentRepository : MyApiRequest() {
    private val studentApi =
        ServiceBuilder.buildService(StudentAPI::class.java)

    suspend fun getAllStudent() :StudentGetResponse{
        return apiRequest {
            studentApi.getAllStudents(ServiceBuilder.token!!)
        }
    }

    suspend fun deleteStudent(id: String) : DeleteStudentResponse{
        return apiRequest {
            studentApi.deleteStudent(ServiceBuilder.token!!,id)
        }
    }
}