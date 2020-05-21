package com.molde.molde.network.service

import com.molde.molde.model.response.CartItemResponse
import com.molde.molde.model.response.CartResponse
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.model.response.OrderResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface CartService {

    @GET("get")
    suspend fun getCart(@Header("Authorization") auth: String): MoldeResponse<CartResponse>

    @POST("{productId}/{qty}/add")
    suspend fun addItemToCart(
        @Header("Authorization") auth: String,
        @Path("productId") productId: Int,
        @Path("qty") qty: Int
    ): MoldeResponse<CartItemResponse>


    @PUT("{cartItemId}/{qty}/update")
    suspend fun updateItem(
        @Header("Authorization") auth: String,
        @Path("cartItemId") cartItemId: Int,
        @Path("qty") qty: Int
    ): MoldeResponse<CartItemResponse>

    @DELETE("{cartItemId}/remove")
    suspend fun removeItemFromCart(
        @Header("Authorization") auth: String,
        @Path("cartItemId") productId: Int
    ): MoldeResponse<String>

    @POST("checkout")
    suspend fun checkout(
        @Header("Authorization") auth: String,
        @Body requestBody: RequestBody
    ): MoldeResponse<OrderResponse>

}