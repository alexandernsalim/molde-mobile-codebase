package com.molde.molde.model.request

data class ReviewRequest(
    val productId: Int,
    val title: String,
    val description: String,
    val rating: String
)