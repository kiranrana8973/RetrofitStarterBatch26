package com.kiran.retrofitstarterbatch26.ui.api

import com.kiran.retrofitstarterbatch26.ui.model.User
import com.kiran.retrofitstarterbatch26.ui.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
// user api
    @POST("auth/register")
    suspend fun registerUser(
        @Body user : User
    ) : Response<UserResponse>

    @POST("auth/login")
    suspend fun login(
        @Body user : User
    ) : Response<UserResponse>



}