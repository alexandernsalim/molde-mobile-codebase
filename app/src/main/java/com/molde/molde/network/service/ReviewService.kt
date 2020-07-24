package com.molde.molde.network.service

import com.molde.molde.model.entity.Review
import com.molde.molde.model.response.MoldeResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface ReviewService {

    @POST("{orderItemId}")
    suspend fun createReview(
        @Header("Authorization") auth: String,
        @Path("orderItemId") orderItemId: Int,
        @Body request: RequestBody
    ): MoldeResponse<Review>

}