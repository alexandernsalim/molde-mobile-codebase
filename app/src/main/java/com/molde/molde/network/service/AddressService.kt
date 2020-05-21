package com.molde.molde.network.service

import com.molde.molde.model.entity.Address
import com.molde.molde.model.entity.ShipmentAddress
import com.molde.molde.model.response.MoldeResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface AddressService {

    @GET("all")
    suspend fun getAllAddress(@Header("Authorization") auth: String): MoldeResponse<List<Address>>

    @GET("shipment")
    suspend fun getShipmentAddress(
        @Header("Authorization") auth: String,
        @Query("addressId") addressId: Int?
    ): MoldeResponse<ShipmentAddress>

    @POST("add")
    suspend fun addAddress(
        @Header("Authorization") auth: String,
        @Body requestBody: RequestBody
    ): MoldeResponse<Address>

    @PUT("{addressId}/update")
    suspend fun updateAddress(
        @Header("Authorization") auth: String,
        @Path("addressId") addressId: Int,
        @Body requestBody: RequestBody
    ): MoldeResponse<Address>

    @PUT("{addressId}/primary")
    suspend fun setAsPrimary(
        @Header("Authorization") auth: String,
        @Path("addressId") addressId: Int
    ): MoldeResponse<Address>

    @DELETE("{addressId}/remove")
    suspend fun removeAddress(
        @Header("Authorization") auth: String,
        @Path("addressId") addressId: Int
    ): MoldeResponse<String>

}