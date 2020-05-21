package com.molde.molde.model.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirWaybill(
    @SerializedName("result")
    val result: RajaOngkirWaybillItem
)