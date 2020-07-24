package com.molde.molde.model.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirWaybillManifestItem(
    @SerializedName("manifest_code")
    val manifestCode: String?,
    @SerializedName("manifest_description")
    val manifestDescription: String?,
    @SerializedName("manifest_date")
    val manifestDate: String?,
    @SerializedName("manifest_time")
    val manifestTime: String?,
    @SerializedName("city_name")
    val cityName: String?
)