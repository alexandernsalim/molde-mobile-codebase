package com.molde.molde.presentation.home

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
import com.molde.molde.databinding.FragmentHomeBinding
import com.molde.molde.model.entity.Product
import com.molde.molde.presentation.product_detail.ProductDetailActivity
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class HomeFragment : Fragment(),
    ProductsAdapter.IProductCommunicator {
    private lateinit var mBinding: FragmentHomeBinding
    private val vModel = HomeViewModel()
    private val products: MutableList<Product> = mutableListOf()
    private var adapter =
        ProductsAdapter(products, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.rvProducts.layoutManager = LinearLayoutManager(context)
        mBinding.rvProducts.adapter = adapter

        vModel.viewModelScope.launch {
            if (!vModel.getAllProduct()) {
                toast("Gagal mengunduh data, coba lagi")
            }
        }

        vModel.productsLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                products.clear()
                products.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun viewDetail(productId: Int) {
        startActivity<ProductDetailActivity>(
            "PRODUCT_ID" to productId
        )
    }

}