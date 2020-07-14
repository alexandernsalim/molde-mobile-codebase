package com.molde.molde.presentation.change_address

import com.molde.molde.model.entity.Address
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager
import java.lang.Exception

class ChangeAddressRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val addressService = RetrofitClient.addressClient()

    suspend fun getAllAddress(): MoldeResponse<List<Address>> {
        return try {
            addressService.getAllAddress(sharedPreferencesManager.getToken())
        } catch (e: Exception) {
            MoldeResponse(500, "Internal Server Error", null)
        }
    }

    suspend fun setAsPrimary(addressId: Int): MoldeResponse<Address> {
        return try {
            addressService.setAsPrimary(sharedPreferencesManager.getToken(), addressId)
        } catch (e: Exception) {
            MoldeResponse(500, "Internal Server Error", null)
        }
    }

}