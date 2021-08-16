package com.kiran.retrofitstarterbatch26.ui.api

import com.kiran.retrofitstarterbatch26.ui.response.DeleteStudentResponse
import com.kiran.retrofitstarterbatch26.ui.response.StudentGetResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface StudentAPI {
    @GET("student/")
    suspend fun getAllStudents(
        @Header("Authorization") token: String
    ):Response<StudentGetResponse>
//
    @DELETE("student/{id}")
    suspend fun deleteStudent(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<DeleteStudentResponse>
}