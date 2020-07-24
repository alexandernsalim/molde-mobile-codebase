package com.molde.molde.network.service

import com.molde.molde.model.rajaongkir.RajaOngkirCity
import com.molde.molde.model.rajaongkir.RajaOngkirCost
import com.molde.molde.model.rajaongkir.RajaOngkirProvince
import com.molde.molde.model.rajaongkir.RajaOngkirWaybill
import com.molde.molde.model.response.RajaOngkirResponse
import retrofit2.http.*

interface RajaOngkirService {

    @POST("cost")
    @FormUrlEncoded
    suspend fun getShipmentCost(
        @Header("key") key: String,
        @Field("origin") origin: String,
        @Field("destination") destination: String,
        @Field("weight") weight: Int,
        @Field("courier") courier: String
    ): RajaOngkirResponse<RajaOngkirCost>

    @GET("province")
    suspend fun getProvinces(
        @Header("key") key: String
    ): RajaOngkirResponse<RajaOngkirProvince>

    @GET("city")
    suspend fun getCities(
        @Header("key") key: String,
        @Query("province") provinceId: String
    ): RajaOngkirResponse<RajaOngkirCity>

    @FormUrlEncoded
    @POST("waybill")
    suspend fun trackWaybill(
        @Header("key") key: String,
        @Field("waybill") waybill: String,
        @Field("courier") courier: String
    ): RajaOngkirResponse<RajaOngkirWaybill>

}