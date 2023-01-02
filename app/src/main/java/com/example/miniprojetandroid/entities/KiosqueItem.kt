package com.example.miniprojetandroid.entities

data class KiosqueItem(
    val Produit: List<Produit>,
    val Service: List<Service>,
    val Status: Int,
    val __v: Int,
    val _id: String,
    val adresse: String,
    val coordenation: String,
    val image: ImageX,
    val name: String,
    val usersID: List<Any>
)