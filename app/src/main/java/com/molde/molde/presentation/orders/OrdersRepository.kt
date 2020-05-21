package com.molde.molde.presentation.orders

import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.model.response.OrderResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager

class OrdersRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val orderService = RetrofitClient.orderClient()

    suspend fun getUserOrder(): MoldeResponse<List<OrderResponse>> {
        return orderService.getUserOrder(sharedPreferencesManager.getToken())
    }

}