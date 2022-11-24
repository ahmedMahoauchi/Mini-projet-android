package com.example.miniprojetandroid.entities

data class KisoqueItem(
    val Status : Int,
    val Produit: List<Produit>,
    val Service: List<Service>,
    val __v: Int,
    val _id: String,
    val adresse: String,
    val coordenation: String,
    val name: String,
    val usersID: List<Any>
)