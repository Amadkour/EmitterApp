package com.example.emitter.accessLayer.API

import com.example.emitter.accessLayer.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

class UserClient {
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    /**
     * object of PostApi Interface
     */
    private var userApi: UserApis? = null

    /**
     * object of this class as a singleton pattern
     */
    private val userClient: UserClient? = null

    constructor() {
        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        userApi = retrofit.create(UserApis::class.java)
    }

    fun getInstance(): UserClient {
        return userClient ?: UserClient()
    }

    fun getAllUsers(): Call<List<User>> {
        return userApi!!.getAllUsers()
    }
}
//////

interface UserApis {
    @GET("users")
    fun getAllUsers(): Call<List<User>>
}