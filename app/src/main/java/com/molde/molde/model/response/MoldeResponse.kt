package com.molde.molde.model.response

import com.google.gson.annotations.SerializedName

data class MoldeResponse<T>(
    @field:SerializedName("code")
    val code: Int,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("data")
    val data: T? = null
)