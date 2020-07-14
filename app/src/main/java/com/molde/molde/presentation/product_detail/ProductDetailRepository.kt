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

    suspend fun getProduct(productId: Int): MoldeResponse<Product> {
        return try {
            productService.getProduct(sharedPreferencesManager.getToken(), productId)
        } catch (e: Exception) {
            MoldeResponse(500, "Internal Server Error", null)
        }
    }

    suspend fun addToCart(productId: Int, qty: Int): MoldeResponse<CartItemResponse> {
        return try {
            cartService.addItemToCart(sharedPreferencesManager.getToken(), productId, qty)
        } catch (e: Exception) {
            MoldeResponse(500, "Internal Server Error", null)
        }
    }

    suspend fun getDiscussions(productId: Int): MoldeResponse<List<Discussion>> {
        return try {
            productService.getDiscussions(sharedPreferencesManager.getToken(), productId)
        } catch (e: Exception) {
            MoldeResponse(500, "Internal Server Error", null)
        }
    }

    suspend fun getReviews(productId: Int): MoldeResponse<List<Review>> {
        return try {
            productService.getReviews(sharedPreferencesManager.getToken(), productId)
        } catch (e: Exception) {
            MoldeResponse(500, "Internal Server Error", null)
        }
    }

}