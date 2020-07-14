package com.molde.molde.presentation.checkout

import com.molde.molde.BuildConfig
import com.molde.molde.model.entity.ShipmentAddress
import com.molde.molde.model.rajaongkir.RajaOngkirCost
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.model.response.OrderResponse
import com.molde.molde.model.response.RajaOngkirResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager
import okhttp3.MultipartBody

class CheckoutRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val rajaOngkirService = RetrofitClient.rajaOngkirClient()
    private val addressService = RetrofitClient.addressClient()
    private val cartService = RetrofitClient.cartClient()

    suspend fun getShipmentAddress(addressId: Int? = null): MoldeResponse<ShipmentAddress> {
        return addressService.getShipmentAddress(sharedPreferencesManager.getToken(), addressId)
    }

    suspend fun getShipmentCost(
        origin: String,
        destination: String,
        weight: Int,
        courier: String
    ): RajaOngkirResponse<RajaOngkirCost> {
        return try {
            rajaOngkirService.getShipmentCost(
                BuildConfig.API_KEY,
                origin,
                destination,
                weight,
                courier
            )
        } catch (e: Exception) {
            RajaOngkirResponse(null)
        }
    }

    suspend fun checkout(
        courier: String,
        address: String,
        originId: String,
        originCity: String,
        destinationId: String,
        destinationCity: String,
        totalPrice: Long,
        totalShipmentPrice: Long,
        totalPaymentPrice: Long,
        recipient: String,
        recipientPhone: String
    ): MoldeResponse<OrderResponse> {
        val requestBody = MultipartBody.Builder()
            .addFormDataPart("courier", courier)
            .addFormDataPart("address", address)
            .addFormDataPart("originId", originId)
            .addFormDataPart("originCity", originCity)
            .addFormDataPart("destinationId", destinationId)
            .addFormDataPart("destinationCity", destinationCity)
            .addFormDataPart("totalPrice", totalPrice.toString())
            .addFormDataPart("totalShipmentPrice", totalShipmentPrice.toString())
            .addFormDataPart("totalPaymentPrice", totalPaymentPrice.toString())
            .addFormDataPart("recipient", recipient)
            .addFormDataPart("recipientPhone", recipientPhone)
            .build()

        return try {
            cartService.checkout(sharedPreferencesManager.getToken(), requestBody)
        } catch (e: Exception) {
            MoldeResponse(500, "Internal Server Error", null)
        }
    }

}