package com.example.miniprojetandroid.entities

data class UploadImageToProductResponse(
    val `file`: String,
    val produit: Any,
    val success: Boolean
)