package com.molde.molde.model.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirWaybillItem(
    @SerializedName("delivered")
    val delivered: String?,
    @SerializedName("summary")
    val summary: RajaOngkirWaybillSummary?,
    @SerializedName("details")
    val details: RajaOngkirWaybillDetails?,
    @SerializedName("delivery_status")
    val deliveryStatus: RajaOngkirWaybillDeliveryStatus?,
    @SerializedName("manifest")
    val manifest: List<RajaOngkirWaybillManifestItem>?
)