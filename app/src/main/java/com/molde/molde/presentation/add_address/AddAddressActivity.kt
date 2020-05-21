package com.molde.molde.presentation.add_address

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityAddAddressBinding
import com.molde.molde.model.rajaongkir.RajaOngkirCityItem
import com.molde.molde.model.rajaongkir.RajaOngkirProvinceItem
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class AddAddressActivity : BaseActivity() {
    private lateinit var mBinding: ActivityAddAddressBinding
    private val vModel =
        AddAddressViewModel()
    private lateinit var provinceId: String
    private lateinit var cityId: String

    private val provinces: MutableList<RajaOngkirProvinceItem> = mutableListOf()
    private val provinceItems: MutableList<String> = mutableListOf()
    private lateinit var provincesAdapter: ArrayAdapter<String>

    private val cities: MutableList<RajaOngkirCityItem> = mutableListOf()
    private val cityItems: MutableList<String> = mutableListOf()
    private lateinit var citiesAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_address)
        mBinding.toolbar.title = "Tambah Alamat Baru"
        mBinding.toolbar.setNavigationOnClickListener {
            finish()
        }

        provincesAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            provinceItems
        )
        mBinding.spProvince.adapter = provincesAdapter
        mBinding.spProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                provinceId = provinces[position].provinceId
                loadCities(provinceId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do Nothing
            }

        }

        citiesAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            cityItems
        )
        mBinding.spCity.adapter = citiesAdapter
        mBinding.spCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                cityId = cities[position].cityId
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do Nothing
            }

        }

        loadProvinces()

        mBinding.btAddAddress.setOnClickListener {
            val saveAs = mBinding.etAddressLabel.text.toString()
            val recipient = mBinding.etRecipient.text.toString()
            val recipientPhone = mBinding.etRecipientPhone.text.toString()
            val street = mBinding.etStreet.text.toString()
            val province = mBinding.spProvince.selectedItem.toString()
            val city = mBinding.spCity.selectedItem.toString()
            val postalCode = mBinding.etPostalCode.text.toString()

            addAddress(
                saveAs,
                recipient,
                recipientPhone,
                provinceId,
                province,
                cityId,
                city,
                street,
                postalCode
            )
        }

        vModel.provincesLiveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                provinces.clear()
                provinces.addAll(it)
                provinceItems.clear()
                provinceItems.addAll(it.map { province ->
                    province.province
                })
                provincesAdapter.notifyDataSetChanged()
            }
        })

        vModel.citiesLiveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                cities.clear()
                cities.addAll(it)
                cityItems.clear()
                cityItems.addAll(it.map { city ->
                    city.cityName
                })
                citiesAdapter.notifyDataSetChanged()
            }
        })

        vModel.addressLiveData.observe(this, Observer {
            if (it != null) {
                finish()
                toast("Alamat berhasil ditambahkan")
            }
        })
    }

    private fun loadProvinces() {
        vModel.viewModelScope.launch {
            if (!vModel.getProvinces()) {
                toast("Gagal memuat provinsi, silahkan coba lagi")
            }
        }
    }

    private fun loadCities(provinceId: String) {
        vModel.viewModelScope.launch {
            if (!vModel.getCities(provinceId)) {
                toast("Gagal memuat kota, silahkan coba lagi")
            }
        }
    }

    private fun addAddress(
        saveAs: String,
        recipient: String,
        recipientPhone: String,
        provinceId: String,
        province: String,
        cityId: String,
        city: String,
        street: String,
        postalCode: String
    ) {
        vModel.viewModelScope.launch {
            if (!vModel.addAddress(
                    saveAs,
                    recipient,
                    recipientPhone,
                    provinceId,
                    province,
                    cityId,
                    city,
                    street,
                    postalCode
                )
            ) {
                toast("Gagal menambahkan alamat, silahkan coba lagi")
            }
        }
    }

}