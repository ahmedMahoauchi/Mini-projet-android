package com.example.miniprojetandroid.entities

data class AddProductToKiosqueResponse(
    val Status: Int,
    val __v: Int,
    val _id: String,
    val adresse: String,
    val coordenation: String,
    val myProduit: List<MyProduit>,
    val myService: List<MyService>,
    val name: String,
    val usersID: List<Any>
)