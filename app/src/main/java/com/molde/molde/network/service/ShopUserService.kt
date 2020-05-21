package com.molde.molde.network.service

import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.model.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ShopUserService {

    @POST("register")
    suspend fun register(
        @Header("SHOP_ID") shopId: Int,
        @Body request: RequestBody
    ): MoldeResponse<RegisterResponse>

}