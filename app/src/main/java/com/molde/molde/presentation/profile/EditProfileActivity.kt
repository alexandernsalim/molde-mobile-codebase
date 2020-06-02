package com.molde.molde.presentation.profile

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityEditProfileBinding
import com.molde.molde.model.request.EditProfileRequest
import com.molde.molde.model.response.ShopUserResponse
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class EditProfileActivity : BaseActivity() {
    private lateinit var mBinding: ActivityEditProfileBinding
    private val vModel = ProfileViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)

        mBinding.toolbar.title = "Ubah Profil"
        mBinding.toolbar.setNavigationOnClickListener { finish() }

        val profile = intent.getParcelableExtra<ShopUserResponse>(ProfileActivity.EXTRA_PROFILE)

        profile?.let {
            mBinding.etFirstName.setText(it.firstName)
            mBinding.etLastName.setText(it.lastName)
            mBinding.etEmail.setText(it.email)
            mBinding.etPhoneNo.setText(it.phoneNo)

            mBinding.btEdit.setOnClickListener {
                val firstName = mBinding.etFirstName.text.toString()
                val lastName = mBinding.etLastName.text.toString()
                val email = mBinding.etEmail.text.toString()
                val phoneNo = mBinding.etPhoneNo.text.toString()
                var isError = false

                if (firstName.isEmpty()) {
                    isError = true
                    mBinding.etFirstName.error = "Nama depan harus diisi"
                }

                if (email.isEmpty()) {
                    isError = true
                    mBinding.etFirstName.error = "Nama depan harus diisi"
                }

                if (phoneNo.isEmpty()) {
                    isError = true
                    mBinding.etFirstName.error = "Nama depan harus diisi"
                }

                if (lastName.isEmpty()) {
                    isError = true
                    mBinding.etFirstName.setText("")
                }

                if (!isError) {
                    val editProfileRequest = EditProfileRequest(
                        firstName,
                        lastName,
                        email,
                        phoneNo
                    )
                    editProfile(editProfileRequest)
                }
            }
        }

        vModel.editProfileLiveData.observe(this, Observer {
            if (it != null) {
                finish()
                toast("Berhasil mengubah data profil")
            }
        })
    }

    private fun editProfile(editProfileRequest: EditProfileRequest) {
        vModel.viewModelScope.launch {
            if (!vModel.editProfile(editProfileRequest)) {
                toast("Gagal mengubah data profil")
            }
        }
    }

}
