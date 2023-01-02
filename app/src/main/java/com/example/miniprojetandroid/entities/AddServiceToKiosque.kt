package com.example.miniprojetandroid.entities

data class AddServiceToKiosque(
    val Status: Int,
    val __v: Int,
    val _id: String,
    val adresse: String,
    val coordenation: String,
    val myProduit: List<Any>,
    val myService: List<MyServiceX>,
    val name: String,
    val usersID: List<Any>
)