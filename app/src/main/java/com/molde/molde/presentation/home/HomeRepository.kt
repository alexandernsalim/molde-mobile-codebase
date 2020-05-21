package com.molde.molde.presentation.home

import com.molde.molde.model.entity.Product
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager

class HomeRepository {
    private val productService = RetrofitClient.productClient()
    private val sharedPreferencesManager = SharedPreferencesManager()

    suspend fun getAllProduct(): MoldeResponse<List<Product>> {
        return productService.getAllProduct(sharedPreferencesManager.getToken())
    }

}