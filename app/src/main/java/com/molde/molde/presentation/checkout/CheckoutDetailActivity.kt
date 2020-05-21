package com.molde.molde.presentation.checkout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityCheckoutDetailBinding
import com.molde.molde.model.entity.ShipmentAddress
import com.molde.molde.model.rajaongkir.RajaOngkirCostItemServices
import com.molde.molde.presentation.change_address.ChangeAddressActivity
import com.molde.molde.presentation.payment_detail.PaymentDetailActivity
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class CheckoutDetailActivity : BaseActivity() {
    private lateinit var mBinding: ActivityCheckoutDetailBinding
    private val vModel =
        CheckoutViewModel()
    private val optionFragment =
        CreatePrimaryAddressOptionFragment()

    private lateinit var courierItems: Array<String>
    private lateinit var courierAdapter: ArrayAdapter<String>

    private val services: MutableList<RajaOngkirCostItemServices> = mutableListOf()
    private val serviceItems: MutableList<String> = mutableListOf()
    private lateinit var servicesAdapter: ArrayAdapter<String>

    private var addressId: Int? = null
    private var shipmentAddress: ShipmentAddress? = null
    private var totalWeight: Int = 0
    private var totalPrice: Long = 0
    private var totalShipmentPrice: Long = 0
    private var totalPaymentPrice: Long = 0
    private var courier = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_checkout_detail)
        mBinding.toolbar.title = "Checkout"
        mBinding.toolbar.setNavigationOnClickListener {
            finish()
        }

        totalPrice = intent.getLongExtra("TOTAL_PRICE", 0)
        totalWeight = intent.getIntExtra("TOTAL_WEIGHT", 0)

        courierItems = resources.getStringArray(R.array.courier)
        courierAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, courierItems)
        mBinding.spCourier.adapter = courierAdapter
        mBinding.spCourier.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                courier = mBinding.spCourier.selectedItem.toString().toLowerCase()
                shipmentAddress?.let {
                    getShipmentCost(it.origin, it.cityId, totalWeight, courier)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do nothing
            }
        }

        servicesAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, serviceItems)
        mBinding.spCourierServices.adapter = servicesAdapter
        mBinding.spCourierServices.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    totalShipmentPrice = services[position].cost[0].value
                    totalPaymentPrice = services[position].cost[0].value + totalPrice
                    mBinding.tvCourierServicesEtd.text = "${services[position].cost[0].etd} Hari"
                    mBinding.tvShipmentPrice.text = "Rp. $totalShipmentPrice"
                    mBinding.tvTotalPayment.text = "Rp. $totalPaymentPrice"
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //Do nothing
                }
            }

        mBinding.tvTotalPrice.text = "Rp. $totalPrice"
        mBinding.btChangeAddress.setOnClickListener {
            val intent = Intent(this, ChangeAddressActivity::class.java)
            intent.putExtra("SELECTED_ADDRESS", addressId)

            startActivityForResult(intent, 1)
        }

        mBinding.btContinuePayment.setOnClickListener {
            shipmentAddress?.let {
                checkout(
                    courier,
                    it.street,
                    it.origin,
                    it.originCity,
                    it.cityId,
                    it.city,
                    totalPrice,
                    totalShipmentPrice,
                    totalPaymentPrice,
                    it.recipient,
                    it.recipientPhone
                )
            }
        }

        vModel.shipmentAddressLiveData.observe(this, Observer {
            if (it != null) {
                shipmentAddress = it
                this.addressId = it.id
                mBinding.tvAddressRecipient.text = it.recipient
                mBinding.tvAddressStreet.text = it.street
                mBinding.tvAddressCity.text = it.city
                mBinding.tvAddressProvince.text = ", ${it.province}"
                mBinding.tvAddressPhone.text = it.recipientPhone
                getShipmentCost(it.origin, it.cityId, totalWeight, courier)
            }
        })

        vModel.costLiveData.observe(this, Observer {
            if (it != null) {
                services.clear()
                services.addAll(it.results[0].services)
                serviceItems.clear()
                serviceItems.addAll(it.results[0].services.map { service ->
                    service.service + " (${service.description})"
                }.toList())
                servicesAdapter.notifyDataSetChanged()
            }
        })

        vModel.orderLiveData.observe(this, Observer {
            if (it != null) {
                toast("Checkout berhasil dilakukan")
                startActivity<PaymentDetailActivity>()
                finish()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        optionFragment.dialog?.let {
            if (it.isShowing) {
                optionFragment.dismiss()
            }
        }
        getShipmentAddress(addressId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                addressId = data?.getIntExtra("ADDRESS_ID", 0)
            }
        }
    }

    private fun getShipmentAddress(addressId: Int? = null) {
        vModel.viewModelScope.launch {
            if (!vModel.getShipmentAddress(addressId)) {
                if (vModel.isAddressEmpty) {
                    optionFragment.show(supportFragmentManager, "OPTION_FRAGMENT")
                } else {
                    toast("Gagal memuat data alamat pengiriman")
                }
            }
        }
    }

    private fun getShipmentCost(origin: String, destination: String, weight: Int, courier: String) {
        vModel.viewModelScope.launch {
            if (!vModel.getShipmentCost(origin, destination, weight, courier)) {
                toast("Gagal memuat data layanan yang tersedia")
            }
        }
    }

    private fun checkout(
        courier: String,
        address: String,
        originId: String,
        originCity: String,
        destinationId: String,
        destinationCity: String,
        totalPrice: Long,
        totalShipmentPrice: Long,
        totalPaymentPrice: Long,
        recipient: String,
        recipientPhone: String
    ) {
        vModel.viewModelScope.launch {
            if (!vModel.checkout(
                    courier,
                    address,
                    originId,
                    originCity,
                    destinationId,
                    destinationCity,
                    totalPrice,
                    totalShipmentPrice,
                    totalPaymentPrice,
                    recipient,
                    recipientPhone
                )
            ) {
                toast("Gagal melanjutkan proses")
            }
        }
    }

}