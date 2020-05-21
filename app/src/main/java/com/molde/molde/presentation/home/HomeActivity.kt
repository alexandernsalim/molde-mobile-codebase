package com.molde.molde.presentation.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.presentation.account.AccountFragment
import com.molde.molde.presentation.cart.CartFragment
import com.molde.molde.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    private lateinit var mBinding: ActivityHomeBinding

    companion object {
        private const val HOME = "HOME_FRAGMENT"
        private const val CART = "CART_FRAGMENT"
        private const val ACCOUNT = "ACCOUNT_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        mBinding.toolbar.title = "Molde Client App"

        bn_main_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    initFragment(
                        HomeFragment(),
                        HOME, R.id.fl_home_content)
                }
                R.id.action_cart -> {
                    initFragment(
                        CartFragment(),
                        CART, R.id.fl_home_content)
                }
                R.id.action_account -> {
                    initFragment(
                        AccountFragment(),
                        ACCOUNT, R.id.fl_home_content)
                }

            }
            true
        }

        bn_main_navigation.selectedItemId = R.id.action_home
        initFragment(
            HomeFragment(),
            HOME, R.id.fl_home_content)
    }

}
