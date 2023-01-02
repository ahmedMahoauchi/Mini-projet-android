package com.example.miniprojetandroid.entities

data class AddKiosqueToUserResponse(
    val CIN: String,
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val image: ImageXXX,
    val myKiosque: List<MyKiosque>,
    val name: String,
    val password: String,
    val role: Int
)