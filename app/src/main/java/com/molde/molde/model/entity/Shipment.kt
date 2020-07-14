package com.molde.molde.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Shipment(
    val id: Int,
    val airwayBill: String? = null,
    val courier: String,
    val address: String,
    val originId: Int,
    val originCity: String,
    val destinationId: Int,
    val destinationCity: String,
    val totalShipmentPrice: Long,
    val recipient: String,
    val recipientPhone: String
) : Parcelable