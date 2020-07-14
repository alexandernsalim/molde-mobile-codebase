package com.molde.molde.presentation.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.entity.Review
import com.molde.molde.model.request.ReviewRequest

class ReviewViewModel : ViewModel() {
    val repository = ReviewRepository()
    val createLiveData = MutableLiveData<Review>()
    val userReviewsLiveData = MutableLiveData<List<Review>>()

    suspend fun getUserReviews(): Boolean {
        val response = repository.getUserReviews()

        return if (response.code == ResponseCode.SUCCESS) {
            userReviewsLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun createReview(orderItemId: Int, reviewRequest: ReviewRequest): Boolean {
        val response = repository.createReview(orderItemId, reviewRequest)

        return if (response.code == ResponseCode.SUCCESS) {
            createLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}