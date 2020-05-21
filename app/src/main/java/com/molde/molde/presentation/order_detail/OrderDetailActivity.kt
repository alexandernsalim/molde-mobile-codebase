package com.molde.molde.presentation.order_detail

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityOrderDetailBinding
import com.molde.molde.model.constant.OrderStatusConstant
import com.molde.molde.model.response.OrderResponse
import com.molde.molde.presentation.payment_detail.PaymentDetailActivity
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailActivity : BaseActivity() {
    private lateinit var mBinding: ActivityOrderDetailBinding
    private val optionFragment = CancelOrderOptionFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        mBinding.toolbar.title = "Detail Pesanan"
        mBinding.toolbar.setNavigationOnClickListener {
            finish()
        }

        //TODO Change to call Order Detail from API
        val product = intent?.getParcelableExtra<OrderResponse>("ORDER_DETAIL")

        product?.let {
            val orderId = it.id
            val dateFormatter = SimpleDateFormat("dd MMMM yyyy")
            val date = Date(it.transactionDate.time)
            val orderItemAdapter =
                OrderItemAdapter(it.items)
            var status: String = ""

            when (it.status) {
                OrderStatusConstant.WAITING_FOR_PAYMENT -> {
                    status = OrderStatusConstant.WAITING_FOR_PAYMENT_RESP
                }
                OrderStatusConstant.WAITING_FOR_PAYMENT_CONFIRMATION -> {
                    status = OrderStatusConstant.WAITING_FOR_PAYMENT_CONFIRMATION_RESP
                    mBinding.btPayNow.visibility = View.GONE
                }
                OrderStatusConstant.PAYMENT_ACCEPTED -> {
                    status = OrderStatusConstant.PAYMENT_ACCEPTED_RESP
                    mBinding.btPayNow.visibility = View.GONE
                }
                OrderStatusConstant.PAYMENT_REJECTED -> {
                    status = OrderStatusConstant.PAYMENT_REJECTED_RESP
                    mBinding.btOrderConfirmed.visibility = View.GONE
                }
                OrderStatusConstant.SHIPMENT_IN_PROGRESS -> {
                    status = OrderStatusConstant.SHIPMENT_IN_PROGRESS_RESP
                    mBinding.tvTrackOrder.visibility = View.VISIBLE
                    mBinding.btPayNow.visibility = View.GONE
                    mBinding.btCancel.visibility = View.GONE
                }
                OrderStatusConstant.ORDER_COMPLETE -> {
                    status = OrderStatusConstant.ORDER_COMPLETE_RESP
                    mBinding.btOrderConfirmed.visibility = View.GONE
                    mBinding.btPayNow.visibility = View.GONE
                    mBinding.btCancel.visibility = View.GONE
                }
                OrderStatusConstant.CANCELLED -> {
                    status = OrderStatusConstant.CANCELLED_RESP
                    mBinding.btOrderConfirmed.visibility = View.GONE
                    mBinding.btPayNow.visibility = View.GONE
                    mBinding.btCancel.visibility = View.GONE
                }
            }

            mBinding.rvOrderItem.layoutManager = LinearLayoutManager(this)
            mBinding.rvOrderItem.adapter = orderItemAdapter
            mBinding.tvTransactionNo.text = it.transactionNo
            mBinding.tvTransactionDate.text = dateFormatter.format(date)
            mBinding.tvStatus.text = status
            mBinding.tvCourier.text = it.shipment.courier.toUpperCase()
            mBinding.tvAirwayBill.text = it.shipment.airwayBill ?: "-"
            mBinding.tvRecipient.text = it.shipment.recipient
            mBinding.tvShipmentAddress.text = it.shipment.address
            mBinding.tvPhoneNo.text = it.shipment.recipientPhone
            mBinding.tvTotalPrice.text = "Rp. ${it.totalPrice}"
            mBinding.tvTotalShipmentPrice.text = "Rp. ${it.shipment.totalShipmentPrice}"
            mBinding.tvTotalPaymentPrice.text = "Rp. ${it.totalPaymentPrice}"

            mBinding.btPayNow.setOnClickListener {
                startActivity<PaymentDetailActivity>(
                    "ORDER_ID" to orderId
                )
            }

            mBinding.btCancel.setOnClickListener {
                val bundle = Bundle()

                bundle.putInt("ORDER_ID", orderId)
                optionFragment.arguments = bundle
                optionFragment.show(supportFragmentManager, "CANCEL_OPTION_FRAGMENT")
            }
        }
    }

}