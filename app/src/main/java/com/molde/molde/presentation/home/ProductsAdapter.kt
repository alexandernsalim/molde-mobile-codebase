package com.molde.molde.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.molde.molde.R
import com.molde.molde.databinding.ItemProductBinding
import com.molde.molde.model.constant.PathConstant
import com.molde.molde.model.entity.Product
import com.squareup.picasso.Picasso

class ProductsAdapter(
    private val products: List<Product>,
    private val communicator: IProductCommunicator
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    private lateinit var mBinding: ItemProductBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_product, parent, false
        )

        return ProductViewHolder(mBinding.root)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(product: Product) {
            mBinding.tvProductName.text = product.name
            mBinding.tvProductPrice.text = "Rp. ${product.price}"
            Picasso.get().load(PathConstant.HOST + product.image).fit()
                .into(mBinding.ivProductImage)
            mBinding.btBuy.setOnClickListener {
                communicator.viewDetail(product.id)
            }
        }

    }

    interface IProductCommunicator {
        fun viewDetail(productId: Int)
    }

}



