package com.molde.molde.network.service

import com.molde.molde.model.entity.Order
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.model.response.OrderResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface OrderService {

    @GET("user/get")
    suspend fun getUserOrder(@Header("Authorization") auth: String): MoldeResponse<List<OrderResponse>>

    @GET("{orderId}/get")
    suspend fun getOrderDetail(
        @Header("Authorization") auth: String,
        @Path("orderId") orderId: Int
    ): MoldeResponse<OrderResponse>

    @Multipart
    @POST("{orderId}/upload-payment")
    suspend fun uploadPaymentImage(
        @Header("Authorization") auth: String,
        @Path("orderId") orderId: Int,
        @Part file: MultipartBody.Part,
        @Query("shopId") shopId: Int
    ): MoldeResponse<Order>

    @POST("{orderId}/cancel")
    suspend fun cancelOrder(
        @Header("Authorization") auth: String,
        @Path("orderId") orderId: Int
    ): MoldeResponse<Order>

}