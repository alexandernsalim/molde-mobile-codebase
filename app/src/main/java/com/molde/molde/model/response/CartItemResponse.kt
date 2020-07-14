package com.molde.molde.model.response

import com.google.gson.annotations.SerializedName
import com.molde.molde.model.entity.Product

data class CartItemResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("product")
    val product: Product,
    @SerializedName("qty")
    val qty: Int,
    @SerializedName("totalWeight")
    val totalWeight: Float,
    @SerializedName("totalPrice")
    val totalPrice: Long
)