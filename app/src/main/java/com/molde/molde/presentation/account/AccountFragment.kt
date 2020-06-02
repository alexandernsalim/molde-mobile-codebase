package com.molde.molde.presentation.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.molde.molde.R
import com.molde.molde.databinding.FragmentAccountBinding
import com.molde.molde.presentation.addresses.AddressesActivity
import com.molde.molde.presentation.discussion.DiscussionListActivity
import com.molde.molde.presentation.login.LoginActivity
import com.molde.molde.presentation.orders.OrdersActivity
import com.molde.molde.presentation.profile.ProfileActivity
import com.molde.molde.presentation.review.ReviewListActivity
import com.molde.molde.util.SharedPreferencesManager
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
        mBinding.btProfile.setOnClickListener {
            startActivity<ProfileActivity>()
        }

        mBinding.btAddresses.setOnClickListener {
            startActivity<AddressesActivity>()
        }

        mBinding.btOrders.setOnClickListener {
            startActivity<OrdersActivity>()
        }

        mBinding.btReviews.setOnClickListener {
            startActivity<ReviewListActivity>()
        }

        mBinding.btDiscussions.setOnClickListener {
            startActivity<DiscussionListActivity>()
        }

        mBinding.btLogout.setOnClickListener {
            val sharedPreferencesManager = SharedPreferencesManager()

            sharedPreferencesManager.clearToken()
            activity?.finish()
            startActivity<LoginActivity>()
        }
    }

}