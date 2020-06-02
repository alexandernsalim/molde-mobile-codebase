package com.molde.molde.presentation.order_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.rajaongkir.RajaOngkirWaybill

class TrackWaybillViewModel : ViewModel() {
    val repository = OrderDetailRepository()
    val waybillLiveData: MutableLiveData<RajaOngkirWaybill> = MutableLiveData()

    suspend fun trackWaybill(waybill: String, courier: String): Boolean {
        val response = repository.trackWaybill(waybill, courier)

        return if (response.data != null) {
            waybillLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}