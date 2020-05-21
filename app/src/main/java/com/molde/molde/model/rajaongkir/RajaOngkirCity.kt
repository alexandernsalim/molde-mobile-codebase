package com.molde.molde.model.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirCity(
    @SerializedName("results")
    val cities: List<RajaOngkirCityItem>
)