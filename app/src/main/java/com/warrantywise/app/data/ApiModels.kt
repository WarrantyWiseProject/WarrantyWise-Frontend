package com.warrantywise.app.data

data class LoginRequest(val email: String, val password: String)

data class RegisterRequest(val name: String, val email: String, val password: String)

data class UserPublic(val id: String, val name: String, val email: String)

data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val user: UserPublic
)

data class ItemCreate(
    val item_name: String,
    val date_purchased: String,
    val warranty_date: String,
    val photo_url: String? = null
)

data class ItemPublic(
    val id: String,
    val item_name: String,
    val date_purchased: String,
    val warranty_date: String,
    val photo: Boolean,
    val photo_url: String?
)

data class MessageResponse(val message: String)
