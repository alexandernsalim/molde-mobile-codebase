package com.molde.molde.model.response

import android.os.Parcelable
import com.molde.molde.model.entity.Product
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderItemResponse(
    val id: Int,
    val product: Product,
    val qty: Int,
    val totalWeight: Float,
    val totalPrice: Long,
    val reviewed: Int
) : Parcelable