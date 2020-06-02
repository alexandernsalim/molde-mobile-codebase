package com.molde.molde.presentation.register

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class RegisterActivity : BaseActivity() {
    private lateinit var mBinding: ActivityRegisterBinding
    private val vModel =
        RegisterViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        mBinding.toolbar.title = "Daftar Akun Baru"
        mBinding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val shopId = resources.getInteger(R.integer.shop_id)

        mBinding.btRegister.setOnClickListener {
            val email = mBinding.etEmail.text.toString()
            val password = mBinding.etPassword.text.toString()
            val confirmPassword = mBinding.etConfirmPassword.text.toString()
            val firstName = mBinding.etFirstName.text.toString()
            val lastName = mBinding.etLastName.text.toString()
            val phoneNo = mBinding.etPhoneNo.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                toast(R.string.required_alert)
            } else {
                vModel.viewModelScope.launch {
                    if (!vModel.register(shopId, email, password, firstName, lastName, phoneNo)) {
                        toast("Failed to register")
                    }
                }
            }
        }

        vModel.registerLiveData.observe(this, Observer {
            if (it != null) {
                toast("Register successfully")
                finish()
            }
        })
    }

}