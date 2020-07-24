package com.molde.molde.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.entity.Product

class HomeViewModel : ViewModel() {
    private val repository = HomeRepository()
    val productsLiveData: MutableLiveData<List<Product>> = MutableLiveData()

    suspend fun getAllProduct(): Boolean {
        val response = repository.getAllProduct()

        return if (response.code == ResponseCode.SUCCESS) {
            productsLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}