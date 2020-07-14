package com.molde.molde.model.entity

import com.google.gson.annotations.SerializedName

data class ShipmentAddress(
    @SerializedName("id")
    val id: Int,
    @SerializedName("recipient")
    val recipient: String,
    @SerializedName("recipientPhone")
    val recipientPhone: String,
    @SerializedName("provinceId")
    val provinceId: String,
    @SerializedName("province")
    val province: String,
    @SerializedName("cityId")
    val cityId: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("postalCode")
    val postalCode: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("originCity")
    val originCity: String
)