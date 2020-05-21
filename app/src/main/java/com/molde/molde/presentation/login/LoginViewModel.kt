package com.molde.molde.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.request.AuthRequest
import com.molde.molde.model.response.AuthResponse

class LoginViewModel : ViewModel() {
    val loginLiveData = MutableLiveData<AuthResponse>()
    private val repository = LoginRepository()

    suspend fun login(request: AuthRequest): Boolean {
        val response = repository.login(request)

        return if (response.code == ResponseCode.SUCCESS) {
            loginLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}