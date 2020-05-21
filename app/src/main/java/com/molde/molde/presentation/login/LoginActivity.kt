package com.molde.molde.presentation.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.molde.molde.R
import com.molde.molde.databinding.ActivityLoginBinding
import com.molde.molde.presentation.home.HomeActivity
import com.molde.molde.model.request.AuthRequest
import com.molde.molde.presentation.register.RegisterActivity
import com.molde.molde.util.SharedPreferencesManager
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding
    private val vModel = LoginViewModel()
    private val sharedPreferencesManager = SharedPreferencesManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        mBinding.btLogin.setOnClickListener {
            val email = mBinding.etEmail.text.toString()
            val password = mBinding.etPassword.text.toString()
            var isError = false

            if (email.isEmpty()) {
                isError = true
                mBinding.etEmail.error = "Email harus diisi"
            }

            if (password.isEmpty()) {
                isError = true
                mBinding.etPassword.error = "Password harus diisi"
            }

            if (!isError) {
                vModel.viewModelScope.launch {
                    if (!vModel.login(AuthRequest(email, password))) {
                        toast(R.string.login_failed)
                    }
                }
            }
        }

        vModel.loginLiveData.observe(this, Observer {
            if (it.token.isNotEmpty()) {
                sharedPreferencesManager.putToken(it.token)
                startActivity<HomeActivity>()
                finish()
            }
        })

        mBinding.btRegister.setOnClickListener {
            startActivity<RegisterActivity>()
        }
    }

}