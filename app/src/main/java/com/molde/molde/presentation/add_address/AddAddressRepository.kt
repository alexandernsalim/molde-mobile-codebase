package com.molde.molde.presentation.add_address

import com.molde.molde.BuildConfig
import com.molde.molde.model.entity.Address
import com.molde.molde.model.rajaongkir.RajaOngkirCity
import com.molde.molde.model.rajaongkir.RajaOngkirProvince
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.model.response.RajaOngkirResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager
import okhttp3.MultipartBody

class AddAddressRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val addressService = RetrofitClient.addressClient()
    private val rajaOngkirService = RetrofitClient.rajaOngkirClient()

    suspend fun addAddress(
        saveAs: String,
        recipient: String,
        recipientPhone: String,
        provinceId: String,
        province: String,
        cityId: String,
        city: String,
        street: String,
        postalCode: String
    ): MoldeResponse<Address> {
        val requestBody = MultipartBody.Builder()
            .addFormDataPart("saveAs", saveAs)
            .addFormDataPart("recipient", recipient)
            .addFormDataPart("recipientPhone", recipientPhone)
            .addFormDataPart("provinceId", provinceId)
            .addFormDataPart("province", province)
            .addFormDataPart("cityId", cityId)
            .addFormDataPart("city", city)
            .addFormDataPart("street", street)
            .addFormDataPart("postalCode", postalCode)
            .build()

        return addressService.addAddress(sharedPreferencesManager.getToken(), requestBody)
    }

    suspend fun getProvinces(): RajaOngkirResponse<RajaOngkirProvince> {
        return rajaOngkirService.getProvinces(BuildConfig.API_KEY)
    }

    suspend fun getCities(provinceId: String): RajaOngkirResponse<RajaOngkirCity> {
        return rajaOngkirService.getCities(BuildConfig.API_KEY, provinceId)
    }

}