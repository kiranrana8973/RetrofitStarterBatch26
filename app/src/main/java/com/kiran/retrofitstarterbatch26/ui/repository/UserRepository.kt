package com.kiran.retrofitstarterbatch26.ui.repository

import com.kiran.retrofitstarterbatch26.ui.api.MyApiRequest
import com.kiran.retrofitstarterbatch26.ui.api.ServiceBuilder
import com.kiran.retrofitstarterbatch26.ui.api.UserAPI
import com.kiran.retrofitstarterbatch26.ui.model.User
import com.kiran.retrofitstarterbatch26.ui.response.UserResponse

class UserRepository : MyApiRequest() {

    private val userApi =
        ServiceBuilder.buildService(UserAPI::class.java)

    //Register user
    suspend fun registerUser(user: User): UserResponse {
        return apiRequest {
            userApi.registerUser(user)
        }
    }

    // Login user
    suspend fun login(username: String, password: String): UserResponse {
        return apiRequest {
            userApi.login(username, password)
        }
    }


}