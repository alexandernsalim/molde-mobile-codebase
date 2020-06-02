package com.molde.molde.model.entity

data class Review(
    val id: Int,
    val title: String,
    val description: String,
    val rating: String,
    val product: Product
)