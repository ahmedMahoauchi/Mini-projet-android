package com.example.miniprojetandroid.network

import com.example.miniprojetandroid.entities.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiUser {

    @GET("users")
    fun getUsers() : Call<User>


    @GET("kiosques")
    fun getKisoques() : Call<Kiosque>

    @GET("kiosque0")
    fun getKisoques0() : Call<Kiosque>


    @POST("login")
    fun login(@Body map : HashMap<String, String> ): Call<LoginResponse>

    @POST("register")
    fun register(@Body map : HashMap<String, String> ): Call<UserX>

    @POST("produit/add")
    fun addProduct(@Body map : HashMap<String, String> ): Call<AddProductResponse>

    @POST("service/add")
    fun addService(@Body map : HashMap<String, String> ): Call<AddServiceResponse>


    @GET("usere/{email}")
    fun getByEmail(@Path("email") email: String? ): Call<GetUserByEmailResponse>

    @GET("kiosque/{id}")
    fun getKiosqueById(@Path("id") id: String? ): Call<SingleKiosqueRes>

    @PUT("forgot-password")
    fun sendMail(@Body map : HashMap<String, String>): Call<ResetResponse>

    @PUT("reset-password")
    fun resetPassword(@Body map : HashMap<String, String>): Call<ResetResponse>

    @DELETE("deletek/{id}")
    fun deleteKiosque(@Path("id") id: String? ): Call<DeleteKiosqueResponse>


    @DELETE("delete/{id}")
    fun deleteUser(@Path("id") id: String? ): Call<DeleteUserResponse>

   @Multipart
   @PUT("uploadphoto/{id}")
   fun uploadImage(@Part image: MultipartBody.Part,@Path("id") id: String? ): Call<UploadImageResponse>


    @Multipart
    @PUT("uploadphoto/{id}")
    fun uploadImageToProduct(@Part image: MultipartBody.Part,@Path("id") id: String? ): Call<UploadImageToProductResponse>

    @Multipart
    @PUT("uploadphoto/{id}")
    fun uploadImageToService(@Part image: MultipartBody.Part,@Path("id") id: String? ): Call<UploadImageToServiceResponse>


    @PUT("profile/{id}")
    fun updateUser(@Body map : HashMap<String, String>,@Path("id") id: String?): Call<UpdateUser>

    @PUT("kiosque/{id}")
    fun updateKiosque(@Body map : HashMap<String, Int>,@Path("id") id: String?): Call<UpdateKiosqueResponse>

    @POST("kiosques/add")
    fun addKiosque(@Body map : HashMap<String, String> ): Call<AddKiosqueResponse>


    @PUT("mykiosque/{id}")
    fun addKiosqueToUser(@Body map : HashMap<String, String>,@Path("id") id: String?): Call<AddKiosqueToUserResponse>

    @PUT("MyProduit/{id}")
    fun addProductToKiosque(@Body map : HashMap<String, String>,@Path("id") id: String?): Call<AddProductToKiosqueResponse>

    @PUT("MyService/{id}")
    fun addServiceToKiosque(@Body map : HashMap<String, String>,@Path("id") id: String?): Call<AddServiceToKiosque>



    companion object {
        //var BASE_URL = "http://192.168.1.6:9092/user/"
        //var BASE_URL = "http://192.168.1.10:9092/user/"
        var BASE_URL = "http://192.168.1.12:9092/user/"

        fun create() : ApiUser {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiUser::class.java)
        }
    }
}
