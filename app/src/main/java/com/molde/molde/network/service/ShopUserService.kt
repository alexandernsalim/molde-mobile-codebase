package com.molde.molde.network.service

import com.molde.molde.model.entity.Discussion
import com.molde.molde.model.entity.Review
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.model.response.ShopUserResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface ShopUserService {

    @POST("register")
    suspend fun register(
        @Header("SHOP_ID") shopId: Int,
        @Body request: RequestBody
    ): MoldeResponse<ShopUserResponse>

    @GET("get")
    suspend fun getInfo(@Header("Authorization") auth: String): MoldeResponse<ShopUserResponse>

    @GET("reviews")
    suspend fun getReviews(@Header("Authorization") auth: String): MoldeResponse<List<Review>>

    @GET("discussions")
    suspend fun getDiscussions(@Header("Authorization") auth: String): MoldeResponse<List<Discussion>>

    @PUT("edit")
    suspend fun editProfile(
        @Header("Authorization") auth: String,
        @Body request: RequestBody
    ): MoldeResponse<ShopUserResponse>

    @PUT("change-password")
    suspend fun changePassword(
        @Header("Authorization") auth: String,
        @Body request: RequestBody
    ): MoldeResponse<String>

}