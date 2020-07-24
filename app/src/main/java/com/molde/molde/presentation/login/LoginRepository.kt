package com.molde.molde.presentation.login

import android.util.Log
import com.molde.molde.model.request.AuthRequest
import com.molde.molde.model.response.AuthResponse
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.network.RetrofitClient

class LoginRepository {
    private val service = RetrofitClient.authenticationClient()

    suspend fun login(shopId: Int, request: AuthRequest): MoldeResponse<AuthResponse> {
        return try {
            service.login(shopId, request)
        } catch (e: Exception) {
            Log.e("LOGIN_ERROR", e.message);
            MoldeResponse(401, "Unauthorized", null)
        }
    }
}
