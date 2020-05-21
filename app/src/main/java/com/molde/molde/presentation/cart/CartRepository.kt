package com.molde.molde.presentation.cart

import com.molde.molde.model.response.CartItemResponse
import com.molde.molde.model.response.CartResponse
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager

class CartRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val cartService = RetrofitClient.cartClient()

    suspend fun getCart(): MoldeResponse<CartResponse> {
        return cartService.getCart(sharedPreferencesManager.getToken())
    }

    suspend fun updateItem(cartItemId: Int, qty: Int): MoldeResponse<CartItemResponse> {
        return cartService.updateItem(sharedPreferencesManager.getToken(), cartItemId, qty)
    }

    suspend fun removeItemFromCart(cartItemId: Int): MoldeResponse<String> {
        return cartService.removeItemFromCart(sharedPreferencesManager.getToken(), cartItemId)
    }

}