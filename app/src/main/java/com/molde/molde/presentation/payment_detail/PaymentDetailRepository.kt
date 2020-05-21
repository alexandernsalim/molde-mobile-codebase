package com.molde.molde.presentation.payment_detail

import com.molde.molde.model.entity.Order
import com.molde.molde.model.response.BankAccountResponse
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PaymentDetailRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val bankService = RetrofitClient.bankAccountClient()
    private val orderService = RetrofitClient.orderClient()

    suspend fun getShopBankAccount(shopId: Int): MoldeResponse<List<BankAccountResponse>> {
        return bankService.getShopBankAccount(sharedPreferencesManager.getToken(), shopId)
    }

    suspend fun uploadPaymentImage(shopId: Int, orderId: Int, file: File): MoldeResponse<Order> {
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        val paymentImage = MultipartBody.Part.createFormData("paymentImage", file.name, requestFile)

        return orderService.uploadPaymentImage(
            sharedPreferencesManager.getToken(),
            orderId,
            paymentImage,
            shopId
        )
    }

}