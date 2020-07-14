package com.molde.molde.presentation.change_address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityChangeAddressBinding
import com.molde.molde.model.entity.Address
import com.molde.molde.presentation.add_address.AddAddressActivity
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ChangeAddressActivity : BaseActivity(),
    ChangeAddressAdapter.IChangeAddressCommunicator {
    private lateinit var mBinding: ActivityChangeAddressBinding
    private val vModel =
        ChangeAddressViewModel()
    private val addresses: MutableList<Address> = mutableListOf()
    private lateinit var adapter: ChangeAddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_address)
        mBinding.toolbar.title = "Pilih Alamat"
        mBinding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val addressId = intent.getIntExtra("SELECTED_ADDRESS", 0)

        adapter =
            ChangeAddressAdapter(
                addressId,
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

    override fun chooseAddress(address: Address) {
        val intent = Intent()

        intent.putExtra("ADDRESS_ID", address.id)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}