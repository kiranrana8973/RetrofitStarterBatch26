package com.kiran.retrofitstarterbatch26.ui.api

import com.kiran.retrofitstarterbatch26.ui.model.Student
import com.kiran.retrofitstarterbatch26.ui.response.AddStudentResponse
import com.kiran.retrofitstarterbatch26.ui.response.DeleteStudentResponse
import com.kiran.retrofitstarterbatch26.ui.response.StudentGetResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface StudentAPI {
    @GET("student/")
    suspend fun getAllStudents(
        @Header("Authorization") token: String
    ): Response<StudentGetResponse>

    //
    @DELETE("student/{id}")
    suspend fun deleteStudent(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<DeleteStudentResponse>

    @POST("student/")
    suspend fun insertStudent(
        @Header("Authorization") token: String,
        @Body student: Student
    ): Response<AddStudentResponse>

    @Multipart
    @PUT("student/{id}/photo")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<DeleteStudentResponse>


}