package com.molde.molde.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.molde.molde.R
import com.molde.molde.databinding.ActivityLoginBinding
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        mBinding.btLogin.setOnClickListener {
            val email = mBinding.etEmail.text
            val password = mBinding.etPassword.text

            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                toast(R.string.required_alert)
            } else {
                TODO("Call login function from LoginViewModel")
            }
        }

        mBinding.btRegister.setOnClickListener {
            startActivity<RegisterActivity>()
        }
    }

}