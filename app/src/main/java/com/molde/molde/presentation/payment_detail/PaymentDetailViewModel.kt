package com.molde.molde.presentation.payment_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.entity.Order
import com.molde.molde.model.response.BankAccountResponse
import java.io.File

class PaymentDetailViewModel : ViewModel() {
    private val repository =
        PaymentDetailRepository()
    val bankAccountsLiveData: MutableLiveData<List<BankAccountResponse>> = MutableLiveData()
    val uploadFileLiveData: MutableLiveData<Order> = MutableLiveData()

    suspend fun getShopBankAccount(shopId: Int): Boolean {
        val response = repository.getShopBankAccount(shopId)

        return if (response.code == ResponseCode.SUCCESS) {
            bankAccountsLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun uploadPaymentImage(shopId: Int, orderId: Int, file: File): Boolean {
        val response = repository.uploadPaymentImage(shopId, orderId, file)

        return if (response.code == ResponseCode.SUCCESS) {
            uploadFileLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}