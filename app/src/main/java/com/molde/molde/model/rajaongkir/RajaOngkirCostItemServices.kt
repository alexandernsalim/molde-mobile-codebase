package com.molde.molde.model.rajaongkir

data class RajaOngkirCostItemServices(
    val service: String,
    val description: String,
    val cost: List<RajaOngkirCostItemServicesCost>
)