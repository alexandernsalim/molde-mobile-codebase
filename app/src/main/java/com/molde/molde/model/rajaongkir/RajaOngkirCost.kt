package com.molde.molde.model.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirCost(
    @SerializedName("results")
    val results: List<RajaOngkirCostItem>
)