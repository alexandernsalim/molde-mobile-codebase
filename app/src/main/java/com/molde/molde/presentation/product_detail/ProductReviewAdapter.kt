package com.molde.molde.presentation.product_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molde.molde.R
import com.molde.molde.model.entity.Review
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_product_review.*

class ProductReviewAdapter : RecyclerView.Adapter<ProductReviewAdapter.ProductReviewViewHolder>() {
    private val reviews: MutableList<Review> = mutableListOf()

    fun setData(reviews: List<Review>) {
        this.reviews.clear()
        this.reviews.addAll(reviews)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductReviewViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_product_review, parent, false)
    )

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: ProductReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    inner class ProductReviewViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(review: Review) {
            with(itemView) {
                tv_title.text = review.title
                tv_description.text = review.description
                rb_service.rating = review.rating.toFloat()
            }
        }

    }
}