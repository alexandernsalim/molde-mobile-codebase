package com.molde.molde.presentation.add_address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.entity.Address
import com.molde.molde.model.rajaongkir.RajaOngkirCityItem
import com.molde.molde.model.rajaongkir.RajaOngkirProvinceItem

class AddAddressViewModel : ViewModel() {
    private val repository =
        AddAddressRepository()
    val addressLiveData: MutableLiveData<Address> = MutableLiveData()
    val provincesLiveData: MutableLiveData<List<RajaOngkirProvinceItem>> = MutableLiveData()
    val citiesLiveData: MutableLiveData<List<RajaOngkirCityItem>> = MutableLiveData()

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
    ): Boolean {
        val response = repository.addAddress(
            saveAs,
            recipient,
            recipientPhone,
            provinceId,
            province,
            cityId,
            city,
            street,
            postalCode
        )

        return if (response.code == ResponseCode.SUCCESS) {
            addressLiveData.postValue(response.data)
            true
        } else {
            false
        }

    }

    suspend fun getProvinces(): Boolean {
        val response = repository.getProvinces()

        return if (response.data?.provinces.isNullOrEmpty()) {
            false
        } else {
            provincesLiveData.postValue(response.data?.provinces)
            true
        }
    }

    suspend fun getCities(provinceId: String): Boolean {
        val response = repository.getCities(provinceId)

        return if (response.data?.cities.isNullOrEmpty()) {
            false
        } else {
            citiesLiveData.postValue(response.data?.cities)
            true
        }
    }

}