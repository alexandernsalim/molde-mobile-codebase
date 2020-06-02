package com.molde.molde.presentation.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molde.molde.R
import com.molde.molde.model.constant.PathConstant
import com.molde.molde.model.entity.Review
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_review.*

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    private val reviews: MutableList<Review> = mutableListOf()

    fun setData(reviews: List<Review>) {
        this.reviews.clear()
        this.reviews.addAll(reviews)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReviewViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
    )

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    inner class ReviewViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(review: Review) {
            with(itemView) {
                Picasso.get().load(PathConstant.HOST + review.product.image).fit()
                    .into(iv_product_image)
                tv_product_name.text = review.product.name
                rb_service.rating = review.rating.toFloat()
                tv_title.text = review.title
            }
        }

    }
}