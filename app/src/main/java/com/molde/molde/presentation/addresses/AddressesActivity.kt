package com.molde.molde.presentation.addresses

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.presentation.add_address.AddAddressActivity
import com.molde.molde.databinding.ActivityAddressesBinding
import com.molde.molde.model.entity.Address
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class AddressesActivity : BaseActivity(),
    AddressAdapter.IAddressCommunicator {
    private lateinit var mBinding: ActivityAddressesBinding
    private val vModel =
        AddressesViewModel()
    private val addresses: MutableList<Address> = mutableListOf()
    private lateinit var adapter: AddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_addresses)
        mBinding.toolbar.title = "Daftar Alamat"
        mBinding.toolbar.setNavigationOnClickListener {
            finish()
        }

        adapter = AddressAdapter(
            addresses,
            this
        )

        mBinding.rvAddresses.layoutManager = LinearLayoutManager(this)
        mBinding.rvAddresses.adapter = adapter

        mBinding.btAddNewAddress.setOnClickListener {
            startActivity<AddAddressActivity>()
        }

        vModel.addressesLiveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                addresses.clear()
                addresses.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        vModel.addressLiveData.observe(this, Observer {
            if (it != null) {
                loadAddress()
                toast("Alamat utama berhasil diubah")
            }
        })

        vModel.removeAddressLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                loadAddress()
                toast("Alamat berhasil dihapus")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadAddress()
    }

    private fun loadAddress() {
        vModel.viewModelScope.launch {
            if (!vModel.getAllAddress()) {
                toast("Gagal memuat data, silahkan coba lagi")
            }
        }
    }

    override fun setAsPrimary(addressId: Int) {
        vModel.viewModelScope.launch {
            if (!vModel.setAsPrimary(addressId)) {
                toast("Gagal mengubah alamat utama, silahkan coba lagi")
            }
        }
    }

    override fun updateAddress(address: Address) {
        //TODO Complete function
    }

    override fun removeAddress(addressId: Int) {
        vModel.viewModelScope.launch {
            if (!vModel.removeAddress(addressId)) {
                toast("Gagal menghapus alamat, silahkan coba lagi")
            }
        }
    }

}