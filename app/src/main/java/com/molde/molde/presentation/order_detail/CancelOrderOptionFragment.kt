package com.molde.molde.presentation.order_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.viewModelScope
import com.molde.molde.R
import com.molde.molde.databinding.FragmentCancelOrderOptionBinding
import com.molde.molde.model.constant.OrderStatusConstant
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.toast

class CancelOrderOptionFragment : DialogFragment() {
    private lateinit var mBinding: FragmentCancelOrderOptionBinding
    private val vModel = OrderDetailViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cancel_order_option,
            container,
            false
        )

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val orderId = arguments?.getInt("ORDER_ID")

        mBinding.btNo.setOnClickListener {
            dismiss()
        }

        mBinding.btYes.setOnClickListener {
            orderId?.let { orderId -> cancelOrder(orderId) }
        }

        vModel.orderLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.status == OrderStatusConstant.CANCELLED) {
                dismiss()
                toast("Pesanan dibatalkan")
            }
        })
    }

    private fun cancelOrder(orderId: Int) {
        vModel.viewModelScope.launch {
            if (!vModel.cancelOrder(orderId)) {
                toast("Terjadi kesalahan, silahkan mencoba lagi")
            }
        }
    }

}