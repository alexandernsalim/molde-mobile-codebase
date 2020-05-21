package com.molde.molde.model.response

import com.google.gson.annotations.SerializedName

data class RajaOngkirResponse<T>(
    @SerializedName("rajaongkir")
    val data: T? = null
)