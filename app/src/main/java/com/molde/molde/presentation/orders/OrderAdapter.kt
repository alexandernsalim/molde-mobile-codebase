package com.molde.molde.presentation.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molde.molde.R
import com.molde.molde.model.constant.PathConstant
import com.molde.molde.model.response.OrderResponse
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order.view.*

class OrderAdapter(
    private val communicator: IOrderCommunicator
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private val orders: MutableList<OrderResponse> = mutableListOf()

    fun setData(orders: List<OrderResponse>) {
        this.orders.clear()
        this.orders.addAll(orders)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
    )

    override fun getItemCount() = orders.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) =
        holder.bind(orders[position])

    inner class OrderViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(order: OrderResponse) {
            val product = order.items[0].product
            itemView.tv_transaction_no.text = order.transactionNo
            itemView.bt_order_detail.setOnClickListener {
                communicator.getOrderDetail(order)
            }
            Picasso.get().load(PathConstant.HOST + product.image).fit()
                .into(itemView.iv_product_image)
            itemView.tv_product_name.text = product.name
            itemView.tv_product_price.text = "Rp. ${product.price}"
        }
    }

    interface IOrderCommunicator {
        fun getOrderDetail(order: OrderResponse)
    }

}