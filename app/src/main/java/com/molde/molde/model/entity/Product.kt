package com.molde.molde.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Long,
    @SerializedName("weight")
    val weight: Float,
    @SerializedName("rating")
    val rating: Float,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("image")
    val image: String
) : Parcelable