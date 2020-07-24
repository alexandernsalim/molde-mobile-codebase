package com.molde.molde.model.request

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
