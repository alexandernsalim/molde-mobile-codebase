package com.molde.molde.presentation.product_detail

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.google.android.material.tabs.TabLayout
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityProductDetailBinding
import com.molde.molde.model.constant.PathConstant
import com.molde.molde.util.invisible
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class ProductDetailActivity : BaseActivity() {
    private lateinit var mBinding: ActivityProductDetailBinding
    private val vModel =
        ProductDetailViewModel()
    private val dFragment =
        ProductDescriptionFragment()

    companion object {
        const val DESCRIPTION = "PRODUCT_DESCRIPTION_FRAGMENT"
        const val DISCUSSION = "PRODUCT_DISCUSSION_FRAGMENT"
        const val REVIEW = "PRODUCT_REVIEW_FRAGMENT"
        const val EXTRA_PRODUCT_ID = "PRODUCT_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        mBinding.toolbar.title = "Product Detail"
        mBinding.toolbar.setNavigationOnClickListener { finish() }

        val productId = intent.getIntExtra("PRODUCT_ID", 0)
        val bundle = Bundle()

        mBinding.btDecQty.setOnClickListener {
            var qty = mBinding.etQty.text.toString().toInt()

            if (qty <= 1) {
                mBinding.etQty.error = "Jumlah pembelian minimal 1"
            } else {
                mBinding.etQty.setText(qty.dec().toString())
            }
        }

        mBinding.btIncQty.setOnClickListener {
            var qty = mBinding.etQty.text.toString().toInt()

            mBinding.etQty.setText(qty.inc().toString())
        }

        mBinding.tlProduct.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                //Do nothing
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                //Do nothing
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                lateinit var fragment: Fragment
                lateinit var tag: String

                when (tab.position) {
                    0 -> {
                        fragment = ProductDescriptionFragment()
                        tag = DESCRIPTION
                    }
                    1 -> {
                        fragment = ProductDiscussionFragment()
                        tag = DISCUSSION
                    }
                    2 -> {
                        fragment = ProductReviewFragment()
                        tag = REVIEW
                    }
                }

                fragment.arguments = bundle
                initFragment(fragment, tag, R.id.fl_product_detail_content)
            }

        })

        mBinding.btAddToCart.setOnClickListener {
            val qty = mBinding.etQty.text.toString().toInt()

            vModel.viewModelScope.launch {
                if (!vModel.addToCart(productId, qty)) {
                    if (vModel.isOutOfStock) {
                        toast("Stock tidak mencukupi jumlah pesanan anda")
                    } else {
                        toast("Gagal menambahkan produk ke keranjang")
                    }
                }
            }
        }

        vModel.viewModelScope.launch {
            if (!vModel.getProduct(productId)) {
                toast("Failed to load data")
            }
        }

        vModel.productLiveData.observe(this, Observer {
            if (it != null) {
                mBinding.pbLoadProduct.invisible()
                Picasso.get().load(PathConstant.HOST + it.image).fit().into(mBinding.ivProductImage)
                mBinding.tvProductName.text = it.name
                mBinding.tvProductPrice.text = "Rp. ${it.price}"
                bundle.putInt(EXTRA_PRODUCT_ID, it.id)
                bundle.putString("PRODUCT_DESCRIPTION", it.description)
                dFragment.arguments = bundle
                initFragment(
                    dFragment,
                    DESCRIPTION, R.id.fl_product_detail_content
                )
            }
        })

        vModel.cartItemLiveData.observe(this, Observer {
            if (it != null) {
                toast("Produk berhasil ditambahkan")
            }
        })
    }

}