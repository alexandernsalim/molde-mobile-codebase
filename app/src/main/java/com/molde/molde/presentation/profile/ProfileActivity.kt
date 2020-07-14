package com.molde.molde.presentation.profile

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityProfileBinding
import com.molde.molde.model.response.ShopUserResponse
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ProfileActivity : BaseActivity() {
    private lateinit var mBinding: ActivityProfileBinding
    private val vModel = ProfileViewModel()

    private lateinit var profile: ShopUserResponse

    companion object {
        const val EXTRA_PROFILE = "PROFILE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        mBinding.toolbar.title = "Profil"
        mBinding.toolbar.setNavigationOnClickListener { finish() }

        mBinding.btEditProfile.setOnClickListener {
            startActivity<EditProfileActivity>(
                EXTRA_PROFILE to profile
            )
        }

        mBinding.btEditPassword.setOnClickListener {
            startActivity<ChangePasswordActivity>()
        }

        vModel.getInfoLiveData.observe(this, Observer {
            if (it != null) {
                profile = it
                mBinding.tvFirstName.text = it.firstName
                mBinding.tvLastName.text = it.lastName
                mBinding.tvEmail.text = it.email
                mBinding.tvPhoneNo.text = it.phoneNo
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getInfo()
    }

    private fun getInfo() {
        vModel.viewModelScope.launch {
            if (!vModel.getInfo()) {
                toast("Gagal mengunduh data")
            }
        }
    }

}