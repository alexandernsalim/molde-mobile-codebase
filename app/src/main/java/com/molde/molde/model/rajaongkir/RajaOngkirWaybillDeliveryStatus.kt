package com.molde.molde.model.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirWaybillDeliveryStatus(
    @SerializedName("status")
    val status: String?,
    @SerializedName("pod_receiver")
    val podReceiver: String?,
    @SerializedName("pod_date")
    val podDate: String?,
    @SerializedName("pod_time")
    val podTime: String?
)