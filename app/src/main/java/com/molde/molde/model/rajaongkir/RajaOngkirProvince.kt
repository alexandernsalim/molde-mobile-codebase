package com.molde.molde.model.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirProvince(
    @SerializedName("results")
    val provinces: List<RajaOngkirProvinceItem>
)