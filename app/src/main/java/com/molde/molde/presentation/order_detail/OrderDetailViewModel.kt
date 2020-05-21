package com.molde.molde.presentation.order_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.entity.Order
import com.molde.molde.model.rajaongkir.RajaOngkirWaybill

class OrderDetailViewModel() : ViewModel() {
    private val repository =
        OrderDetailRepository()
    val orderLiveData: MutableLiveData<Order> = MutableLiveData()
    val waybillLiveData: MutableLiveData<RajaOngkirWaybill> = MutableLiveData()

    suspend fun trackWaybill(waybill: String, courier: String): Boolean {
        val response = repository.trackWaybill(waybill, courier)

        return if (response.data != null) {
            waybillLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun cancelOrder(orderId: Int): Boolean {
        val response = repository.cancelOrder(orderId)

        return if (response.code == ResponseCode.SUCCESS) {
            orderLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}