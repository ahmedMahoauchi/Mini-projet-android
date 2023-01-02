package com.example.miniprojetandroid.entities

data class UserX(
    val resetLink : String,
    val CIN: String,
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val image: Image,
    val myKiosque: List<Any>,
    val name: String,
    val password: String,
    val role: Int
)