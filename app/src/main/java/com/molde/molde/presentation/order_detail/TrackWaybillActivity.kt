package com.molde.molde.presentation.order_detail

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityTrackWaybillBinding
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class TrackWaybillActivity : BaseActivity() {
    private lateinit var mBinding: ActivityTrackWaybillBinding
    private val vModel = TrackWaybillViewModel()
    private val adapter = ManifestsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_track_waybill)
        mBinding.toolbar.title = "Lacak Pengiriman"
        mBinding.toolbar.setNavigationOnClickListener { finish() }

        val waybill = intent?.getStringExtra("WAYBILL")
        val courier = intent?.getStringExtra("COURIER")

        mBinding.rvShipmentManifests.layoutManager = LinearLayoutManager(this)
        mBinding.rvShipmentManifests.adapter = adapter

        if (waybill != null && courier != null) {
            trackWaybill(waybill, courier)
        }

        vModel.waybillLiveData.observe(this, Observer {
            if (it.result.manifest != null) {
                mBinding.tvShipmentSender.text = it.result.details?.shipperName
                mBinding.tvShipmentOrigin.text = it.result.details?.shipperCity
                mBinding.tvShipmentService.text = it.result.summary?.courierCode
                mBinding.tvShipmentWaybill.text = it.result.summary?.waybillNumber
                adapter.setData(it.result.manifest)
            }
        })
    }

    fun trackWaybill(waybill: String, courier: String) {
        vModel.viewModelScope.launch {
            if (!vModel.trackWaybill(
                    waybill,
                    courier
                )
            ) toast("Gagal memuat data")
        }
    }

}