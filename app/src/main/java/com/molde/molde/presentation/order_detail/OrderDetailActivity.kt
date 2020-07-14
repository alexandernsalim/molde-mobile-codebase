package com.molde.molde.presentation.order_detail

import android.app.AlertDialog
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityOrderDetailBinding
import com.molde.molde.model.constant.OrderStatusConstant
import com.molde.molde.model.entity.Shipment
import com.molde.molde.model.response.OrderItemResponse
import com.molde.molde.presentation.payment_detail.PaymentDetailActivity
import com.molde.molde.presentation.review.ReviewActivity
import com.molde.molde.util.gone
import com.molde.molde.util.invisible
import com.molde.molde.util.visible
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailActivity : BaseActivity(), OrderItemAdapter.IOrderItemCommunicator {
    private lateinit var mBinding: ActivityOrderDetailBinding
    private val vModel = OrderDetailViewModel()

    private lateinit var shipment: Shipment
    private var orderId: Int? = null

    companion object {
        const val ALERT_DIALOG_CONFIRM = 200
        const val ALERT_DIALOG_CANCEL = 201
        const val EXTRA_PRODUCT_ID = "PRODUCT_ID"
        const val EXTRA_ORDER_ITEM_ID = "ORDER_ITEM_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)

        mBinding.toolbar.title = "Detail Pesanan"
        mBinding.toolbar.setNavigationOnClickListener {
            finish()
        }

        orderId = intent?.getIntExtra("ORDER_ID", 0)

        vModel.orderDetailLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                shipment = it.shipment
                val dateFormatter = SimpleDateFormat("dd MMMM yyyy")
                val date = Date(it.transactionDate.time)
                var status = ""

                when (it.status) {
                    OrderStatusConstant.WAITING_FOR_PAYMENT -> {
                        status = OrderStatusConstant.WAITING_FOR_PAYMENT_RESP
                        mBinding.btPayNow.visible()
                        mBinding.btCancel.visible()
                    }
                    OrderStatusConstant.WAITING_FOR_PAYMENT_CONFIRMATION -> {
                        status = OrderStatusConstant.WAITING_FOR_PAYMENT_CONFIRMATION_RESP
                        mBinding.btPayNow.gone()
                        mBinding.btCancel.gone()
                    }
                    OrderStatusConstant.PAYMENT_ACCEPTED -> {
                        status = OrderStatusConstant.PAYMENT_ACCEPTED_RESP
                    }
                    OrderStatusConstant.PAYMENT_REJECTED -> {
                        status = OrderStatusConstant.PAYMENT_REJECTED_RESP
                        mBinding.btPayNow.visible()
                        mBinding.btCancel.visible()
                    }
                    OrderStatusConstant.SHIPMENT_IN_PROGRESS -> {
                        status = OrderStatusConstant.SHIPMENT_IN_PROGRESS_RESP
                        mBinding.btOrderConfirmed.visible()
                    }
                    OrderStatusConstant.ORDER_COMPLETE -> {
                        status = OrderStatusConstant.ORDER_COMPLETE_RESP
                        mBinding.btOrderConfirmed.gone()
                    }
                    OrderStatusConstant.CANCELLED -> {
                        status = OrderStatusConstant.CANCELLED_RESP
                        mBinding.btPayNow.gone()
                        mBinding.btCancel.gone()
                    }
                }

                if (it.shipment.airwayBill != null) {
                    mBinding.tvTrackOrder.visible()
                }

                val adapter = OrderItemAdapter(this, it.status)
                mBinding.rvOrderItem.layoutManager = LinearLayoutManager(this)
                mBinding.rvOrderItem.adapter = adapter
                adapter.setData(it.items)

                mBinding.tvTransactionNo.text = it.transactionNo
                mBinding.tvTransactionDate.text = dateFormatter.format(date)
                mBinding.tvStatus.text = status
                mBinding.tvCourier.text = it.shipment.courier.toUpperCase()
                mBinding.tvAirwayBill.text = it.shipment.airwayBill ?: "-"
                mBinding.tvRecipient.text = it.shipment.recipient
                mBinding.tvShipmentAddress.text = it.shipment.address
                mBinding.tvPhoneNo.text = it.shipment.recipientPhone
                mBinding.tvTotalPrice.text = "Rp. ${it.totalPrice}"
                mBinding.tvTotalShipmentPrice.text = "Rp. ${shipment.totalShipmentPrice}"
                mBinding.tvTotalPaymentPrice.text = "Rp. ${it.totalPaymentPrice}"
            }
        })

        mBinding.tvTrackOrder.setOnClickListener {
            startActivity<TrackWaybillActivity>(
                "WAYBILL" to shipment.airwayBill,
                "COURIER" to shipment.courier
            )
        }

        mBinding.btPayNow.setOnClickListener {
            startActivity<PaymentDetailActivity>(
                "ORDER_ID" to orderId
            )
        }

        mBinding.btOrderConfirmed.setOnClickListener {
            orderId?.let {
                showAlertDialog(ALERT_DIALOG_CONFIRM)
            }
        }

        mBinding.btCancel.setOnClickListener {
            orderId?.let {
                showAlertDialog(ALERT_DIALOG_CANCEL)
            }
        }

        vModel.completeOrderLiveData.observe(this, androidx.lifecycle.Observer {
            if (it.status == OrderStatusConstant.ORDER_COMPLETE) {
                orderId?.let { id -> getOrderDetail(id) }
                toast("Konfirmasi pesanan diterima berhasil")
            }
        })

        vModel.cancelOrderLiveData.observe(this, androidx.lifecycle.Observer {
            if (it.status == OrderStatusConstant.CANCELLED) {
                orderId?.let { id -> getOrderDetail(id) }
                toast("Pesanan dibatalkan")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        orderId?.let { getOrderDetail(it) }
    }

    private fun getOrderDetail(orderId: Int) {
        vModel.viewModelScope.launch {
            if (!vModel.getOrderDetail(orderId)) {
                toast("Gagal mengunduh data")
            }
        }
    }

    private fun completeOrder(orderId: Int) {
        vModel.viewModelScope.launch {
            if (!vModel.completeOrder(orderId)) {
                toast("Terjadi kesalahan, silahkan mencoba lagi")
            }
        }
    }

    private fun cancelOrder(orderId: Int) {
        vModel.viewModelScope.launch {
            if (!vModel.cancelOrder(orderId)) {
                toast("Terjadi kesalahan, silahkan mencoba lagi")
            }
        }
    }

    private fun showAlertDialog(type: Int) {
        val title: String
        val message: String

        if (type == ALERT_DIALOG_CANCEL) {
            title = "Batalkan Pesanan?"
            message = "Apakah anda yakin untuk membatalkan pesanan ini?"
        } else {
            title = "Pesanan Diterima?"
            message = "Apakah pesanan telah anda terima?"
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ya") { _, _ ->
                if (type == ALERT_DIALOG_CANCEL) {
                    orderId?.let { cancelOrder(it) }
                } else {
                    orderId?.let { completeOrder(it) }
                }
            }
            .setNegativeButton("Tidak") { dialog, id -> dialog.cancel() }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun review(orderItem: OrderItemResponse) {
        startActivity<ReviewActivity>(
            EXTRA_ORDER_ITEM_ID to orderItem.id,
            EXTRA_PRODUCT_ID to orderItem.product.id
        )
    }

}