package com.molde.molde.presentation.order_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.entity.Order
import com.molde.molde.model.response.OrderResponse

class OrderDetailViewModel : ViewModel() {
    private val repository =
        OrderDetailRepository()
    val orderDetailLiveData: MutableLiveData<OrderResponse> = MutableLiveData()
    val completeOrderLiveData: MutableLiveData<Order> = MutableLiveData()
    val cancelOrderLiveData: MutableLiveData<Order> = MutableLiveData()

    suspend fun getOrderDetail(orderId: Int): Boolean {
        val response = repository.getOrder(orderId)

        return if (response.code == ResponseCode.SUCCESS) {
            orderDetailLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun completeOrder(orderId: Int): Boolean {
        val response = repository.completeOrder(orderId)

        return if (response.code == ResponseCode.SUCCESS) {
            completeOrderLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun cancelOrder(orderId: Int): Boolean {
        val response = repository.cancelOrder(orderId)

        return if (response.code == ResponseCode.SUCCESS) {
            cancelOrderLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}