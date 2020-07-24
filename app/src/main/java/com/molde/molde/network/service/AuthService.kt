package com.molde.molde.network.service

import com.molde.molde.model.request.AuthRequest
import com.molde.molde.model.response.AuthResponse
import com.molde.molde.model.response.MoldeResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    suspend fun login(@Header("SHOP_ID") shopId: Int, @Body authRequest: AuthRequest): MoldeResponse<AuthResponse>

}