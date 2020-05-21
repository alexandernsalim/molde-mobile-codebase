package com.molde.molde.network.service

import com.molde.molde.model.entity.Discussion
import com.molde.molde.model.entity.Product
import com.molde.molde.model.response.MoldeResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ProductService {

    @GET("all")
    suspend fun getAllProduct(@Header("Authorization") auth: String): MoldeResponse<List<Product>>

    @GET("{productId}/detail")
    suspend fun getProduct(
        @Header("Authorization") auth: String,
        @Path("productId") productId: Int
    ): MoldeResponse<Product>

    @GET("{productId}/discussion")
    suspend fun getDiscussions(
        @Header("Authorization") auth: String,
        @Path("productId") productId: Int
    ): MoldeResponse<List<Discussion>>

}