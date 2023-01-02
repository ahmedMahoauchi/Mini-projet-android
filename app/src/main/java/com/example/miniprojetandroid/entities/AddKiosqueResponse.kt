package com.example.miniprojetandroid.entities

data class AddKiosqueResponse(
    val Produit: List<Any>,
    val Service: List<Any>,
    val Status: Int,
    val __v: Int,
    val _id: String,
    val adresse: String,
    val coordenation: String,
    val name: String,
    val usersID: List<Any>
)