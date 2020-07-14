package com.molde.molde.presentation.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.entity.ShipmentAddress
import com.molde.molde.model.rajaongkir.RajaOngkirCost
import com.molde.molde.model.response.OrderResponse

class CheckoutViewModel : ViewModel() {
    val costLiveData: MutableLiveData<RajaOngkirCost> = MutableLiveData()
    val shipmentAddressLiveData: MutableLiveData<ShipmentAddress> = MutableLiveData()
    val orderLiveData: MutableLiveData<OrderResponse> = MutableLiveData()
    private val checkoutRepository =
        CheckoutRepository()
    var isAddressEmpty = false

    suspend fun getShipmentAddress(addressId: Int? = null): Boolean {
        val response = checkoutRepository.getShipmentAddress(addressId)

        return when (response.code) {
            ResponseCode.SUCCESS -> {
                shipmentAddressLiveData.postValue(response.data)
                true
            }
            ResponseCode.BAD_REQUEST -> {
                isAddressEmpty = true
                false
            }
            else -> {
                false
            }
        }
    }

    suspend fun getShipmentCost(
        origin: String,
        destination: String,
        weight: Int,
        courier: String
    ): Boolean {
        val response = checkoutRepository.getShipmentCost(origin, destination, weight, courier)

        return if (response.data != null) {
            costLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun checkout(
        courier: String,
        address: String,
        originId: String,
        originCity: String,
        destinationId: String,
        destinationCity: String,
        totalPrice: Long,
        totalShipmentPrice: Long,
        totalPaymentPrice: Long,
        recipient: String,
        recipientPhone: String
    ): Boolean {
        val response = checkoutRepository.checkout(
            courier,
            address,
            originId,
            originCity,
            destinationId,
            destinationCity,
            totalPrice,
            totalShipmentPrice,
            totalPaymentPrice,
            recipient,
            recipientPhone
        )

        return if (response.code == ResponseCode.SUCCESS) {
            orderLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}