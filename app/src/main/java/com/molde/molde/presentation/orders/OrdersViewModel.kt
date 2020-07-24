package com.molde.molde.presentation.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.response.OrderResponse

class OrdersViewModel : ViewModel() {
    private val repository =
        OrdersRepository()
    val ordersLiveData: MutableLiveData<List<OrderResponse>> = MutableLiveData()

    suspend fun getUserOrder(): Boolean {
        val response = repository.getUserOrder()

        return if (response.code == ResponseCode.SUCCESS) {
            ordersLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}