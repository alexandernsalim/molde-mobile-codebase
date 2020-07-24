package com.molde.molde.model.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirProvinceItem(
    @SerializedName("province_id")
    val provinceId: String,
    @SerializedName("province")
    val province: String
)