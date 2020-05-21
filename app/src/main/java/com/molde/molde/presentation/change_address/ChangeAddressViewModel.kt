package com.molde.molde.presentation.change_address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.entity.Address

class ChangeAddressViewModel : ViewModel() {
    private val repository =
        ChangeAddressRepository()
    val addressesLiveData: MutableLiveData<List<Address>> = MutableLiveData()

    suspend fun getAllAddress(): Boolean {
        val response = repository.getAllAddress()

        return if (response.code == ResponseCode.SUCCESS) {
            addressesLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}