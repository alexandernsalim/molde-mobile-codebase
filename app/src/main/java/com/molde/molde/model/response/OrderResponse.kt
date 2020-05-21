package com.molde.molde.model.response

import android.os.Parcelable
import com.molde.molde.model.entity.Shipment
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Parcelize
data class OrderResponse(
    val id: Int,
    val transactionNo: String,
    val transactionDate: Timestamp,
    val items: List<OrderItemResponse>,
    val totalItem: Int,
    val totalPrice: Long,
    val totalPaymentPrice: Long,
    val status: String,
    val shipment: Shipment
) : Parcelable