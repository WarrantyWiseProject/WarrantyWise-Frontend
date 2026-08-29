package com.warrantywise.app.data

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface WarrantyApi {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): TokenResponse

    @POST("login")
    suspend fun login(@Body request: LoginRequest): TokenResponse

    @GET("get-all")
    suspend fun getAllItems(): List<ItemPublic>

    @GET("get-one")
    suspend fun getOneItem(@Query("id") id: String): ItemPublic

    @POST("create")
    suspend fun createItem(@Body request: ItemCreate): ItemPublic

    @PUT("user/update")
    suspend fun updateUser(@Body request: Map<String, String>): UserPublic

    @DELETE("user/delete")
    suspend fun deleteUser(): MessageResponse
}
