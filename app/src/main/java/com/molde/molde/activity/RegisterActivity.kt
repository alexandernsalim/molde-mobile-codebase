package com.molde.molde.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.molde.molde.R
import com.molde.molde.databinding.ActivityRegisterBinding
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        mBinding.btRegister.setOnClickListener {
            val email = mBinding.etEmail.text
            val password = mBinding.etPassword.text
            val confirmPassword = mBinding.etConfirmPassword.text

            if (email.isNullOrEmpty() || password.isNullOrEmpty() || confirmPassword.isNullOrEmpty()) {
                toast(R.string.required_alert)
            } else {
                TODO("Call register function from RegisterViewModel")
            }
        }

    }
}