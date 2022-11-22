package com.example.miniprojetandroid.network

import com.example.miniprojetandroid.entities.LoginResponse
import com.example.miniprojetandroid.entities.User
import com.example.miniprojetandroid.entities.UserX
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiUser {

    @GET("users")
    fun getUsers() : Call<User>

    @POST("login")
    fun login(@Body map : HashMap<String, String> ): Call<LoginResponse>



    companion object {
<<<<<<< Updated upstream
        var BASE_URL = "http://192.168.1.4:9092/user/"
=======
        //var BASE_URL = "http://192.168.1.6:9092/user/"
        var BASE_URL = "http://172.16.7.17:9092/user/"

>>>>>>> Stashed changes
        fun create() : ApiUser {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiUser::class.java)
        }
    }
}
