package com.molde.molde.presentation.orders

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityOrdersBinding
import com.molde.molde.model.response.OrderResponse
import com.molde.molde.presentation.order_detail.OrderDetailActivity
import com.molde.molde.util.invisible
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class OrdersActivity : BaseActivity(),
    OrderAdapter.IOrderCommunicator {
    private lateinit var mBinding: ActivityOrdersBinding
    private val vModel = OrdersViewModel()
    private val adapter = OrderAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_orders)
        mBinding.toolbar.title = "Daftar Pesanan"
        mBinding.toolbar.setNavigationOnClickListener {
            finish()
        }

        mBinding.rvOrders.layoutManager = LinearLayoutManager(this)
        mBinding.rvOrders.adapter = adapter

        vModel.ordersLiveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                mBinding.tvOrdersCond.invisible()
                adapter.setData(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getUserOrder()
    }

    private fun getUserOrder() {
        vModel.viewModelScope.launch {
            if (!vModel.getUserOrder()) {
                toast("Gagal memuat data pesanan")
            }
        }
    }

    override fun getOrderDetail(order: OrderResponse) {
        startActivity<OrderDetailActivity>(
            "ORDER_ID" to order.id
        )
    }

}