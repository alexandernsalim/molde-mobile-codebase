package com.molde.molde.presentation.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.response.ShopUserResponse

class RegisterViewModel : ViewModel() {
    private val repository =
        RegisterRepository()
    val registerLiveData = MutableLiveData<ShopUserResponse>()

    suspend fun register(
        shopId: Int,
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNo: String
    ): Boolean {
        val response = repository.register(shopId, email, password, firstName, lastName, phoneNo)

        return if (response.code == ResponseCode.SUCCESS) {
            registerLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}