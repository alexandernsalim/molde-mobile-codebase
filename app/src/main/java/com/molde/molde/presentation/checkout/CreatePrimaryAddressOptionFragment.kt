package com.molde.molde.presentation.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.molde.molde.R
import com.molde.molde.databinding.FragmentCreatePrimaryAddressOptionBinding
import com.molde.molde.presentation.add_address.AddAddressActivity
import org.jetbrains.anko.support.v4.startActivity

class CreatePrimaryAddressOptionFragment : DialogFragment() {
    private lateinit var mBinding: FragmentCreatePrimaryAddressOptionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_primary_address_option,
            container,
            false
        )

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.btNo.setOnClickListener {
            activity?.finish()
        }

        mBinding.btYes.setOnClickListener {
            startActivity<AddAddressActivity>()
        }
    }

}