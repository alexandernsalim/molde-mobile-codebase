package com.molde.molde.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.request.ChangePasswordRequest
import com.molde.molde.model.request.EditProfileRequest
import com.molde.molde.model.response.ShopUserResponse

class ProfileViewModel : ViewModel() {
    private val repository = ProfileRepository()
    val getInfoLiveData = MutableLiveData<ShopUserResponse>()
    val editProfileLiveData = MutableLiveData<ShopUserResponse>()
    val changePasswordLiveData = MutableLiveData<String>()

    suspend fun getInfo(): Boolean {
        val response = repository.getInfo()

        return if (response.code == ResponseCode.SUCCESS) {
            getInfoLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun editProfile(editProfileRequest: EditProfileRequest): Boolean {
        val response = repository.editProfile(editProfileRequest)

        return if (response.code == ResponseCode.SUCCESS) {
            editProfileLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Boolean {
        val response = repository.changePassword(changePasswordRequest)

        return if (response.code == ResponseCode.SUCCESS) {
            changePasswordLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}