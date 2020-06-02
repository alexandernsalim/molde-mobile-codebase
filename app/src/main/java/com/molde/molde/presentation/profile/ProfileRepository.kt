package com.molde.molde.presentation.profile

import com.molde.molde.model.request.ChangePasswordRequest
import com.molde.molde.model.request.EditProfileRequest
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.model.response.ShopUserResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager
import okhttp3.MultipartBody

class ProfileRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val shopUserService = RetrofitClient.shopUserClient()

    suspend fun getInfo(): MoldeResponse<ShopUserResponse> {
        return shopUserService.getInfo(sharedPreferencesManager.getToken())
    }

    suspend fun editProfile(editProfileRequest: EditProfileRequest): MoldeResponse<ShopUserResponse> {
        val request = MultipartBody.Builder()
            .addFormDataPart("firstName", editProfileRequest.firstName)
            .addFormDataPart("lastName", editProfileRequest.lastName)
            .addFormDataPart("email", editProfileRequest.email)
            .addFormDataPart("phoneNo", editProfileRequest.phoneNo)
            .build()

        return shopUserService.editProfile(sharedPreferencesManager.getToken(), request)
    }

    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): MoldeResponse<String> {
        val request = MultipartBody.Builder()
            .addFormDataPart("oldPassword", changePasswordRequest.oldPassword)
            .addFormDataPart("newPassword", changePasswordRequest.newPassword)
            .build()

        return shopUserService.changePassword(sharedPreferencesManager.getToken(), request)
    }

}