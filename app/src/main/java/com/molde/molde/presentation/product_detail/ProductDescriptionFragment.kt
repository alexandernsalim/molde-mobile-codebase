package com.molde.molde.presentation.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.molde.molde.R
import com.molde.molde.databinding.FragmentProductDescriptionBinding

class ProductDescriptionFragment : Fragment() {
    private lateinit var mBinding: FragmentProductDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product_description,
            container,
            false
        )

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val productDescription = arguments?.getString("PRODUCT_DESCRIPTION")

        mBinding.tvProductDescription.text = productDescription
    }

}