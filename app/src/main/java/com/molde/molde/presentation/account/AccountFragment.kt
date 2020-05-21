package com.molde.molde.presentation.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.molde.molde.R
import com.molde.molde.presentation.addresses.AddressesActivity
import com.molde.molde.databinding.FragmentAccountBinding
import com.molde.molde.presentation.orders.OrdersActivity
import org.jetbrains.anko.support.v4.startActivity

class AccountFragment : Fragment() {
    private lateinit var mBinding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.btAddresses.setOnClickListener {
            startActivity<AddressesActivity>()
        }

        mBinding.btOrders.setOnClickListener {
            startActivity<OrdersActivity>()
        }
    }

}