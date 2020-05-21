package com.molde.molde.model.response

import com.google.gson.annotations.SerializedName
import com.molde.molde.model.entity.Bank

data class BankAccountResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("no")
    val no: String,
    @SerializedName("bank")
    val bank: Bank
)