package com.molde.molde.presentation.register

import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.model.response.ShopUserResponse
import com.molde.molde.network.RetrofitClient
import okhttp3.MultipartBody

class RegisterRepository {
    private val service = RetrofitClient.shopUserClient()

    suspend fun register(
        shopId: Int,
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNo: String
    ): MoldeResponse<ShopUserResponse> {
        val request = MultipartBody.Builder()
            .addFormDataPart("email", email)
            .addFormDataPart("password", password)
            .addFormDataPart("firstName", firstName)
            .addFormDataPart("lastName", lastName)
            .addFormDataPart("phoneNo", phoneNo)
            .build()

        return service.register(shopId, request)
    }

}