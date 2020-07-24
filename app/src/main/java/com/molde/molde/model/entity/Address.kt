package com.molde.molde.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    @SerializedName("id")
    val id: Int,
    @SerializedName("saveAs")
    val saveAs: String,
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
    @SerializedName("primaryAddress")
    val primaryAddress: Boolean
) : Parcelable