package com.molde.molde.presentation.change_address

import com.molde.molde.model.entity.Address
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager

class ChangeAddressRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val addressService = RetrofitClient.addressClient()

    suspend fun getAllAddress(): MoldeResponse<List<Address>> {
        return addressService.getAllAddress(sharedPreferencesManager.getToken())
    }

    suspend fun setAsPrimary(addressId: Int): MoldeResponse<Address> {
        return addressService.setAsPrimary(sharedPreferencesManager.getToken(), addressId)
    }

}