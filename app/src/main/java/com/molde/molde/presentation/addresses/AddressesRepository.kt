package com.molde.molde.presentation.addresses

import com.molde.molde.model.entity.Address
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager

class AddressesRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val addressService = RetrofitClient.addressClient()

    suspend fun getAllAddress(): MoldeResponse<List<Address>> {
        return addressService.getAllAddress(sharedPreferencesManager.getToken())
    }

    suspend fun setAsPrimary(addressId: Int): MoldeResponse<Address> {
        return addressService.setAsPrimary(sharedPreferencesManager.getToken(), addressId)
    }

    suspend fun removeAddress(addressId: Int): MoldeResponse<String> {
        return addressService.removeAddress(sharedPreferencesManager.getToken(), addressId)
    }

}