package com.molde.molde.presentation.addresses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.entity.Address

class AddressesViewModel : ViewModel() {
    private val repository =
        AddressesRepository()
    val addressesLiveData: MutableLiveData<List<Address>> = MutableLiveData()
    val addressLiveData: MutableLiveData<Address> = MutableLiveData()
    val removeAddressLiveData: MutableLiveData<String> = MutableLiveData()

    suspend fun getAllAddress(): Boolean {
        val response = repository.getAllAddress()

        return if (response.code == ResponseCode.SUCCESS) {
            addressesLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun setAsPrimary(addressId: Int): Boolean {
        val response = repository.setAsPrimary(addressId)

        return if (response.code == ResponseCode.SUCCESS) {
            addressLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun removeAddress(addressId: Int): Boolean {
        val response = repository.removeAddress(addressId)

        return if (response.code == ResponseCode.SUCCESS) {
            removeAddressLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}