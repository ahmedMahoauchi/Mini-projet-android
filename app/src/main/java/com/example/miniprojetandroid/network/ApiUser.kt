package com.example.miniprojetandroid.network

import com.example.miniprojetandroid.entities.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiUser {

    @GET("users")
    fun getUsers() : Call<User>

    @POST("login")
    fun login(@Body map : HashMap<String, String> ): Call<LoginResponse>

    @POST("register")
    fun register(@Body map : HashMap<String, String> ): Call<UserX>

    @GET("usere/{email}")
    fun getByEmail(@Path("email") email: String? ): Call<GetUserByEmailResponse>

    @PUT("forgot-password")
    fun sendMail(@Body map : HashMap<String, String>): Call<ResetResponse>

    @PUT("reset-password")
    fun resetPassword(@Body map : HashMap<String, String>): Call<ResetResponse>



    companion object {
        //var BASE_URL = "http://192.168.1.6:9092/user/"
        var BASE_URL = "http://172.16.7.17:9092/user/"

        fun create() : ApiUser {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiUser::class.java)
        }
    }
}
