package com.molde.molde.presentation.product_detail

import com.molde.molde.model.entity.Discussion
import com.molde.molde.model.entity.Product
import com.molde.molde.model.entity.Review
import com.molde.molde.model.response.CartItemResponse
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager

class ProductDetailRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val productService = RetrofitClient.productClient()
    private val cartService = RetrofitClient.cartClient()
    private val discussionService = RetrofitClient.discussionClient()

    suspend fun getProduct(productId: Int): MoldeResponse<Product> {
        return productService.getProduct(sharedPreferencesManager.getToken(), productId)
    }

    suspend fun addToCart(productId: Int, qty: Int): MoldeResponse<CartItemResponse> {
        return cartService.addItemToCart(sharedPreferencesManager.getToken(), productId, qty)
    }

    suspend fun getDiscussions(productId: Int): MoldeResponse<List<Discussion>> {
        return productService.getDiscussions(sharedPreferencesManager.getToken(), productId)
    }

    suspend fun getReviews(productId: Int): MoldeResponse<List<Review>> {
        return productService.getReviews(sharedPreferencesManager.getToken(), productId)
    }

}