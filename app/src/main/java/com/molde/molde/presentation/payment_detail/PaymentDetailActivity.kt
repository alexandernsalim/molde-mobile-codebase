package com.molde.molde.presentation.payment_detail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityPaymentDetailBinding
import com.molde.molde.model.response.BankAccountResponse
import com.molde.molde.presentation.home.HomeActivity
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import pub.devrel.easypermissions.EasyPermissions
import java.io.File


class PaymentDetailActivity : BaseActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var mBinding: ActivityPaymentDetailBinding
    private val vModel =
        PaymentDetailViewModel()
    private var shopId: Int? = null
    private var orderId: Int? = null

    private val bankAccounts: MutableList<BankAccountResponse> = mutableListOf()
    private val bankItems: MutableList<String> = mutableListOf()
    private lateinit var bankAdapter: ArrayAdapter<String>

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment_detail)
        mBinding.toolbar.title = "Detail Pembayaran"
        mBinding.toolbar.setNavigationOnClickListener {
            finish()
        }

        shopId = resources.getInteger(R.integer.shop_id)
        orderId = intent?.getIntExtra("ORDER_ID", 0)

        bankAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bankItems)
        mBinding.spBank.adapter = bankAdapter
        mBinding.spBank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mBinding.tvBankAccountOwner.text = bankAccounts[position].owner
                mBinding.tvBankAccount.text = bankAccounts[position].no
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do Nothing
            }
        }

        shopId?.let {
            getBankAccounts(it)
        }
        mBinding.btUploadPaymentImage.setOnClickListener {
            checkRequiredPermissions()
        }

        mBinding.btHome.setOnClickListener {
            startActivity<HomeActivity>()
            finish()
        }

        vModel.uploadFileLiveData.observe(this, Observer {
            if (it != null) {
                finish()
                toast("Pembayaran berhasil dilakukan, menunggu persetujuan")
            }
        })

        vModel.bankAccountsLiveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                bankAccounts.clear()
                bankAccounts.addAll(it)
                bankItems.clear()
                bankItems.addAll(it.map { bank ->
                    bank.bank.name
                })
                bankAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun checkRequiredPermissions() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            pickImage()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Give access to read file?",
                PERMISSION_CODE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        toast("Permission denied")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        pickImage()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val uri = data?.data
            val path = com.ipaulpro.afilechooser.utils.FileUtils.getPath(this, uri)
            val file = File(path)

            shopId?.let { orderId?.let { order -> uploadPaymentImage(it, order, file) } }
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)

        intent.type = "image/*"
        startActivityForResult(
            intent,
            IMAGE_PICK_CODE
        )
    }

    private fun getRealPathFromUri(contentUri: Uri): String? {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = contentResolver.query(contentUri, proj, null, null, null)
            assert(cursor != null)
            val columnIndex: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            columnIndex?.let { cursor?.getString(it) }
        } finally {
            cursor?.close()
        }
    }

    private fun uploadPaymentImage(shopId: Int, orderId: Int, file: File) {
        vModel.viewModelScope.launch {
            if (!vModel.uploadPaymentImage(shopId, orderId, file)) {
                toast("Gagal mengunggah bukti pembayaran")
            }
        }
    }

    private fun getBankAccounts(shopId: Int) {
        vModel.viewModelScope.launch {
            if (!vModel.getShopBankAccount(shopId)) {
                toast("Gagal memuat data")
            }
        }
    }

}