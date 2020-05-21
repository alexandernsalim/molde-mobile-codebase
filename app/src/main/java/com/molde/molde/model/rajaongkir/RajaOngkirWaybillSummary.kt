package com.molde.molde.model.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirWaybillSummary(
    @SerializedName("courier_code")
    val courierCode: String?,
    @SerializedName("courier_name")
    val courierName: String?,
    @SerializedName("waybill_number")
    val waybillNumber: String?,
    @SerializedName("service_code")
    val serviceCode: String?,
    @SerializedName("waybill_date")
    val waybillDate: String?,
    @SerializedName("shipper_name")
    val shipperName: String?,
    @SerializedName("receiver_name")
    val receiverName: String?,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("destination")
    val destination: String?,
    @SerializedName("status")
    val status: String?
)