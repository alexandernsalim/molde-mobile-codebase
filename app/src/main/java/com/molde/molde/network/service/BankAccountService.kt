package com.molde.molde.network.service

import com.molde.molde.model.response.BankAccountResponse
import com.molde.molde.model.response.MoldeResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BankAccountService {

    @GET("get")
    suspend fun getShopBankAccount(
        @Header("Authorization") auth: String,
        @Query("shopId") shopId: Int
    ): MoldeResponse<List<BankAccountResponse>>

}