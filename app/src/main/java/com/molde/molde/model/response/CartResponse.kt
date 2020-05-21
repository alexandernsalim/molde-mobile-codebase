package com.molde.molde.model.response

import com.google.gson.annotations.SerializedName

data class CartResponse(
    @SerializedName("items")
    val cartItems: List<CartItemResponse>,
    @SerializedName("totalItem")
    val totalItem: Int,
    @SerializedName("totalWeight")
    val totalWeight: Int,
    @SerializedName("totalPrice")
    val totalPrice: Long
)