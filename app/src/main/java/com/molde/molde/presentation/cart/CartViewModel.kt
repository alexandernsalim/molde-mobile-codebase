package com.molde.molde.presentation.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.response.CartItemResponse
import com.molde.molde.model.response.CartResponse

class CartViewModel : ViewModel() {
    private val repository = CartRepository()
    val cartLiveData = MutableLiveData<CartResponse>()
    val cartItemLiveData = MutableLiveData<CartItemResponse>()
    val removeItemLiveData = MutableLiveData<String>()
    var isOutOfStock = false

    suspend fun getCart(): Boolean {
        val response = repository.getCart()

        return if (response.code == ResponseCode.SUCCESS) {
            cartLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun updateItem(cartItemId: Int, qty: Int): Boolean {
        val response = repository.updateItem(cartItemId, qty)

        return when (response.code) {
            ResponseCode.SUCCESS -> {
                cartItemLiveData.postValue(response.data)
                true
            }
            ResponseCode.BAD_REQUEST -> {
                isOutOfStock = true
                false
            }
            else -> {
                false
            }
        }
    }

    suspend fun removeItemFromCart(cartItemId: Int): Boolean {
        val response = repository.removeItemFromCart(cartItemId)

        return if (response.code == ResponseCode.SUCCESS) {
            removeItemLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}
