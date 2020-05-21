package com.molde.molde.model.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirWaybillDetails(
    @SerializedName("waybill_number")
    val waybillNumber: String?,
    @SerializedName("waybill_date")
    val waybillDate: String?,
    @SerializedName("waybill_time")
    val waybillTime: String?,
    @SerializedName("weight")
    val weight: String?,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("destination")
    val destination: String?,
    @SerializedName("shippper_name")
    val shipperName: String?,
    @SerializedName("shipper_address1")
    val shipperAddress1: String?,
    @SerializedName("shipper_address2")
    val shipperAddress2: String?,
    @SerializedName("shipper_address3")
    val shipperAddress3: String?,
    @SerializedName("shipper_city")
    val shipperCity: String?,
    @SerializedName("receiver_name")
    val receiverName: String?,
    @SerializedName("receiver_address1")
    val receiverAddress1: String?,
    @SerializedName("receiver_address2")
    val receiverAddress2: String?,
    @SerializedName("receiver_address3")
    val receiverAddress3: String?,
    @SerializedName("receiver_city")
    val receiverCity: String?
)