package com.molde.molde.presentation.cart

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
import com.molde.molde.databinding.FragmentCartBinding
import com.molde.molde.presentation.checkout.CheckoutDetailActivity
import com.molde.molde.util.invisible
import com.molde.molde.util.visible
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class CartFragment : Fragment(),
    CartItemsAdapter.ICartItemCommunicator {
    private lateinit var mBinding: FragmentCartBinding
    private val vModel = CartViewModel()
    private val adapter = CartItemsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cart,
            container,
            false
        )

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.rvCartItems.layoutManager = LinearLayoutManager(context)
        mBinding.rvCartItems.adapter = adapter

        vModel.cartLiveData.observe(viewLifecycleOwner, Observer {
            if (it.cartItems.isNotEmpty()) {
                adapter.setData(it.cartItems)
                mBinding.tvCartCond.invisible()
                mBinding.btCheckout.visible()
                mBinding.tvTotalItem.text = "Total (${it.totalItem}):"
                mBinding.tvTotalPrice.text = "Rp. ${it.totalPrice}"
                mBinding.btCheckout.setOnClickListener { _ ->
                    startActivity<CheckoutDetailActivity>(
                        "TOTAL_PRICE" to it.totalPrice,
                        "TOTAL_WEIGHT" to it.totalWeight
                    )
                }
            } else {
                adapter.clearData()
                mBinding.tvCartCond.visible()
                mBinding.btCheckout.invisible()
                mBinding.tvTotalItem.text = "Total (${it.totalItem}):"
                mBinding.tvTotalPrice.text = "Rp. ${it.totalPrice}"
            }
        })

        vModel.cartItemLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                loadCart()
                toast("Berhasil mengubah produk")
            }
        })

        vModel.removeItemLiveData.observe(viewLifecycleOwner, Observer {
            if (it == "Item removed successfully") {
                loadCart()
                toast("Berhasil menghapus produk")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadCart()
    }

    private fun loadCart() {
        vModel.viewModelScope.launch {
            if (!vModel.getCart()) {
                toast("Gagal mengunduh data, coba lagi")
            }
        }
    }

    override fun updateItem(cartItemId: Int, qty: Int) {
        vModel.viewModelScope.launch {
            if (!vModel.updateItem(cartItemId, qty)) {
                if (vModel.isOutOfStock) {
                    toast("Stock tidak mencukupi jumlah pesanan anda")
                } else {
                    toast("Gagal mengubah jumlah pesanan, coba lagi")
                }
            }
        }
    }

    override fun removeItem(cartItemId: Int) {
        vModel.viewModelScope.launch {
            if (!vModel.removeItemFromCart(cartItemId)) {
                toast("Gagal menghapus produk")
            }
        }
    }

}