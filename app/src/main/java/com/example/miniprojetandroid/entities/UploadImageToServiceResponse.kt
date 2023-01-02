package com.example.miniprojetandroid.entities

data class UploadImageToServiceResponse(
    val `file`: String,
    val service: Any,
    val success: Boolean
)