package com.molde.molde.presentation.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molde.molde.R
import com.molde.molde.model.constant.PathConstant
import com.molde.molde.model.response.CartItemResponse
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_cart_item.view.*
import kotlinx.android.synthetic.main.item_product.view.iv_product_image
import kotlinx.android.synthetic.main.item_product.view.tv_product_name
import kotlinx.android.synthetic.main.item_product.view.tv_product_price

class CartItemsAdapter(
    private val communicator: ICartItemCommunicator
) : RecyclerView.Adapter<CartItemsAdapter.CartItemViewHolder>() {
    private val cartItems: MutableList<CartItemResponse> = mutableListOf()

    fun setData(cartItems: List<CartItemResponse>) {
        this.cartItems.clear()
        this.cartItems.addAll(cartItems)
        notifyDataSetChanged()
    }

    fun clearData() {
        cartItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart_item, parent, false)
        )
    }

    override fun getItemCount() = cartItems.size

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) =
        holder.bind(cartItems[position])

    inner class CartItemViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(cartItem: CartItemResponse) {
            val product = cartItem.product

            with(itemView) {
                Picasso.get().load(PathConstant.HOST + product.image).fit()
                    .into(iv_product_image)
                tv_product_price.text = "Rp. ${product.price}"
                tv_product_name.text = product.name
                et_qty.setText(cartItem.qty.toString())
                val qty = et_qty.text.toString().toInt()
                bt_dec_qty.setOnClickListener {
                    if (qty <= 1) {
                        et_qty.error = "Jumlah pembelian minimal 1"
                    } else {
                        communicator.updateItem(cartItem.id, qty.dec())
                    }
                }
                bt_inc_qty.setOnClickListener {
                    communicator.updateItem(cartItem.id, qty.inc())
                }
                bt_remove_from_cart.setOnClickListener {
                    communicator.removeItem(cartItem.id)
                }
            }
        }
    }

    interface ICartItemCommunicator {
        fun updateItem(cartItemId: Int, qty: Int)
        fun removeItem(cartItemId: Int)
    }
}