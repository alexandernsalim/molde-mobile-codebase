package com.molde.molde.presentation.order_detail

import com.molde.molde.BuildConfig
import com.molde.molde.model.entity.Order
import com.molde.molde.model.rajaongkir.RajaOngkirWaybill
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.model.response.RajaOngkirResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager

class OrderDetailRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val orderService = RetrofitClient.orderClient()
    private val rajaOngkirService = RetrofitClient.rajaOngkirClient()

    suspend fun trackWaybill(
        waybill: String,
        courier: String
    ): RajaOngkirResponse<RajaOngkirWaybill> {
        return rajaOngkirService.trackWaybill(BuildConfig.API_KEY, waybill, courier)
    }

    suspend fun cancelOrder(orderId: Int): MoldeResponse<Order> {
        return orderService.cancelOrder(sharedPreferencesManager.getToken(), orderId)
    }

}