package com.molde.molde.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molde.molde.R
import com.molde.molde.model.constant.PathConstant
import com.molde.molde.model.entity.Product
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_product.*

class ProductsAdapter(
    private val communicator: IProductCommunicator
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    private val products: MutableList<Product> = mutableListOf()

    fun setData(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        if (parent.context.resources.getString(R.string.layout) == "linear") {
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_grid, parent, false)
        }
    )

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    inner class ProductViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(product: Product) {
            with(itemView) {
                tv_product_name.text = product.name
                tv_product_price.text = "Rp. ${product.price}"
                Picasso.get().load(PathConstant.HOST + product.image).fit()
                    .into(iv_product_image)
                bt_buy.setOnClickListener {
                    communicator.viewDetail(product.id)
                }
            }
        }
    }

    interface IProductCommunicator {
        fun viewDetail(productId: Int)
    }
}



