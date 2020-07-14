package com.molde.molde.presentation.cart

import com.molde.molde.model.response.CartItemResponse
import com.molde.molde.model.response.CartResponse
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager
import java.lang.Exception

class CartRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val cartService = RetrofitClient.cartClient()

    suspend fun getCart(): MoldeResponse<CartResponse> {
        return try {
            cartService.getCart(sharedPreferencesManager.getToken())
        } catch (e: Exception) {
            MoldeResponse(500, "Internal Server Error", null)
        }
    }

    suspend fun updateItem(cartItemId: Int, qty: Int): MoldeResponse<CartItemResponse> {
        return try {
            cartService.updateItem(sharedPreferencesManager.getToken(), cartItemId, qty)
        } catch (e: Exception) {
            MoldeResponse(500, "Internal Server Error", null)
        }
    }

    suspend fun removeItemFromCart(cartItemId: Int): MoldeResponse<String> {
        return try {
            cartService.removeItemFromCart(sharedPreferencesManager.getToken(), cartItemId)
        } catch (e: Exception) {
            MoldeResponse(500, "Internal Server Error", null)
        }
    }

}