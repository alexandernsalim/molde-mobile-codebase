package com.molde.molde.model.request

data class EditProfileRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNo: String
)