package com.molde.molde.model.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirCostItem(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("costs")
    val services: List<RajaOngkirCostItemServices>
)