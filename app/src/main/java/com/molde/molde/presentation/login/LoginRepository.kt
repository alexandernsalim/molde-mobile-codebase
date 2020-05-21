package com.molde.molde.presentation.login

import com.molde.molde.model.request.AuthRequest
import com.molde.molde.model.response.AuthResponse
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.network.RetrofitClient

class LoginRepository {
    private val service = RetrofitClient.authenticationClient()

    suspend fun login(request: AuthRequest): MoldeResponse<AuthResponse> {
        return service.login(request)
    }

}