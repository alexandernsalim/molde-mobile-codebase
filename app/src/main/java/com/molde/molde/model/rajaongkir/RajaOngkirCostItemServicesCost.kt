package com.molde.molde.model.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirCostItemServicesCost(
    @SerializedName("value")
    val value: Long,
    @SerializedName("etd")
    val etd: String,
    @SerializedName("note")
    val note: String
)