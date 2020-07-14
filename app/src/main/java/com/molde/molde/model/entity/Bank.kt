package com.molde.molde.model.entity

import com.google.gson.annotations.SerializedName

data class Bank(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)