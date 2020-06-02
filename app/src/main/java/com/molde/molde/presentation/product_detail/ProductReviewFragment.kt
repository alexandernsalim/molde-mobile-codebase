package com.molde.molde.presentation.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.molde.molde.R
import com.molde.molde.databinding.FragmentProductReviewBinding
import com.molde.molde.util.invisible
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.toast

class ProductReviewFragment : Fragment() {
    private lateinit var mBinding: FragmentProductReviewBinding
    private val vModel = ProductDetailViewModel()
    private lateinit var adapterProduct: ProductReviewAdapter

    private var productId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product_review,
            container,
            false
        )

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productId = arguments?.getInt(ProductDetailActivity.EXTRA_PRODUCT_ID)

        adapterProduct = ProductReviewAdapter()
        mBinding.rvReviews.layoutManager = LinearLayoutManager(context)
        mBinding.rvReviews.adapter = adapterProduct

        vModel.reviewsLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                mBinding.tvReviewsCond.invisible()
                adapterProduct.setData(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        productId?.let { getReviews(it) }
    }

    fun getReviews(productId: Int) {
        vModel.viewModelScope.launch {
            if (!vModel.getReviews(productId)) {
                toast("Gagal mengunduh data")
            }
        }
    }

}