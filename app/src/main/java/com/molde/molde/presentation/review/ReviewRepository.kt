package com.molde.molde.presentation.review

import com.molde.molde.model.entity.Review
import com.molde.molde.model.request.ReviewRequest
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager
import okhttp3.MultipartBody

class ReviewRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val reviewService = RetrofitClient.reviewClient()
    private val shopUserService = RetrofitClient.shopUserClient()

    suspend fun getUserReviews(): MoldeResponse<List<Review>> {
        return shopUserService.getReviews(sharedPreferencesManager.getToken())
    }

    suspend fun createReview(orderItemId: Int, review: ReviewRequest): MoldeResponse<Review> {
        val request = MultipartBody.Builder()
            .addFormDataPart("productId", review.productId.toString())
            .addFormDataPart("title", review.title)
            .addFormDataPart("description", review.description)
            .addFormDataPart("rating", review.rating)
            .build()

        return try {
            reviewService.createReview(sharedPreferencesManager.getToken(), orderItemId, request)
        } catch (e: Exception) {
            MoldeResponse(500, "Internal Server Error", null)
        }
    }

}