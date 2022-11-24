package com.example.miniprojetandroid.entities

data class KiosqueItem(
    val Produit: List<ProduitX>,
    val Service: List<ServiceX>,
    val Status: Int,
    val __v: Int,
    val _id: String,
    val adresse: String,
    val coordenation: String,
    val name: String,
    val usersID: List<Any>
)