package com.molde.molde.presentation.product_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.entity.Discussion
import com.molde.molde.model.entity.Product
import com.molde.molde.model.entity.Review
import com.molde.molde.model.response.CartItemResponse

class ProductDetailViewModel : ViewModel() {
    private val repository =
        ProductDetailRepository()
    val productLiveData = MutableLiveData<Product>()
    val cartItemLiveData = MutableLiveData<CartItemResponse>()
    val discussionsLiveData = MutableLiveData<List<Discussion>>()
    val reviewsLiveData = MutableLiveData<List<Review>>()
    var isOutOfStock = false

    suspend fun getProduct(productId: Int): Boolean {
        val response = repository.getProduct(productId)

        return if (response.code == ResponseCode.SUCCESS) {
            productLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun addToCart(productId: Int, qty: Int): Boolean {
        val response = repository.addToCart(productId, qty)

        return when (response.code) {
            ResponseCode.SUCCESS -> {
                cartItemLiveData.postValue(response.data)
                true
            }
            ResponseCode.BAD_REQUEST -> {
                isOutOfStock = true
                false
            }
            else -> {
                false
            }
        }
    }

    suspend fun getDiscussions(productId: Int): Boolean {
        val response = repository.getDiscussions(productId)

        return if (response.code == ResponseCode.SUCCESS) {
            discussionsLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun getReviews(productId: Int): Boolean {
        val response = repository.getReviews(productId)

        return if (response.code == ResponseCode.SUCCESS) {
            reviewsLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}
