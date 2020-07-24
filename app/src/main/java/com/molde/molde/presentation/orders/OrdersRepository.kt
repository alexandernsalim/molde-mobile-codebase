package com.molde.molde.presentation.orders

import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.model.response.OrderResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager
import java.lang.Exception

class OrdersRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val orderService = RetrofitClient.orderClient()

    suspend fun getUserOrder(): MoldeResponse<List<OrderResponse>> {
        return try {
            orderService.getUserOrder(sharedPreferencesManager.getToken())
        } catch (e: Exception) {
            MoldeResponse(500, "Internal Server Error", null)
        }
    }

}