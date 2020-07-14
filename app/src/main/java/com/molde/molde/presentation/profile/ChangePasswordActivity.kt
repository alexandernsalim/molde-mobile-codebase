package com.molde.molde.presentation.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.molde.molde.R
import com.molde.molde.databinding.ActivityChangePasswordBinding
import com.molde.molde.model.request.ChangePasswordRequest
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityChangePasswordBinding
    private val vModel = ProfileViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)

        mBinding.toolbar.title = "Ubah Password"
        mBinding.toolbar.setNavigationOnClickListener { finish() }

        mBinding.btEdit.setOnClickListener {
            val oldPassword = mBinding.etOldPassword.text.toString()
            val newPassword = mBinding.etNewPassword.text.toString()
            val confirmPassword = mBinding.etConfirmPassword.text.toString()
            var isError: Boolean

            if (oldPassword.isEmpty()) {
                isError = true
                mBinding.etOldPassword.error = "Password sebelumnya harus diisi"
            }

            if (newPassword.isEmpty()) {
                isError = true
                mBinding.etNewPassword.error = "Password baru harus diisi"
            }

            if (confirmPassword.isEmpty()) {
                isError = true
                mBinding.etConfirmPassword.error = "Konfirmasi password harus diisi"
            }

            if (newPassword != confirmPassword) {
                isError = true
                toast("Password baru tidak sama dengan konfirmasi password")
            } else {
                isError = false
            }

            if (!isError) {
                val changePasswordRequest = ChangePasswordRequest(
                    oldPassword,
                    newPassword
                )
                changePassword(changePasswordRequest)
            }
        }

        vModel.changePasswordLiveData.observe(this, Observer {
            if (it == "Password changed") {
                finish()
                toast("Berhasil mengubah password")
            }
        })
    }

    private fun changePassword(changePasswordRequest: ChangePasswordRequest) {
        vModel.viewModelScope.launch {
            if (!vModel.changePassword(changePasswordRequest)) {
                toast("Gagal mengubah password")
            }
        }
    }

}
