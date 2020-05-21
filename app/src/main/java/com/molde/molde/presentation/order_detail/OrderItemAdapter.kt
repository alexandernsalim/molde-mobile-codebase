package com.molde.molde.presentation.order_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molde.molde.R
import com.molde.molde.model.constant.PathConstant
import com.molde.molde.model.response.OrderItemResponse
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order_item.view.*

class OrderItemAdapter(
    private val orderItems: List<OrderItemResponse>
) : RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_order_item, parent, false)
    )

    override fun getItemCount() = orderItems.size

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) =
        holder.bind(orderItems[position])

    inner class OrderItemViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(orderItem: OrderItemResponse) {
            val product = orderItem.product
            Picasso.get().load(PathConstant.HOST + product.image).fit()
                .into(itemView.iv_product_image)
            itemView.tv_product_name.text = product.name
            itemView.tv_qty.text = orderItem.qty.toString()
            itemView.tv_product_price.text = "Rp. ${product.price} / unit"
        }

    }

}