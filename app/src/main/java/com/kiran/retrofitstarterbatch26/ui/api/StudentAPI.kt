package com.kiran.retrofitstarterbatch26.ui.api

import com.kiran.retrofitstarterbatch26.ui.response.StudentGetResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface StudentAPI {
    @GET("student/")
    suspend fun getAllStudents(
        @Header("Authorization") token: String
    ):Response<StudentGetResponse>


}