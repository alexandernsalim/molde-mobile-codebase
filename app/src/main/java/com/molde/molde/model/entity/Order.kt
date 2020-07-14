package com.molde.molde.model.entity

import java.sql.Timestamp

data class Order(
    val id: Int? = null,
    val transactionNo: String? = null,
    val transactionDate: Timestamp? = null,
    val totalPrice: Long = 0,
    val totalPaymentPrice: Long = 0,
    val status: String? = null,
    val shipment: Shipment? = null,
    val shopUserId: Int? = null,
    val paymentImage: String? = null
)