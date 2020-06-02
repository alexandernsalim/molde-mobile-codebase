package com.molde.molde.presentation.discussion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molde.molde.R
import com.molde.molde.model.constant.PathConstant
import com.molde.molde.model.entity.Discussion
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_discussion.view.*

class DiscussionAdapter(
    private val communicator: IDiscussionCommunicator
) : RecyclerView.Adapter<DiscussionAdapter.DiscussionViewHolder>() {
    private val discussions: MutableList<Discussion> = mutableListOf()

    fun setData(discussions: List<Discussion>) {
        this.discussions.clear()
        this.discussions.addAll(discussions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DiscussionViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_discussion, parent, false)
    )

    override fun getItemCount() = discussions.size

    override fun onBindViewHolder(holder: DiscussionViewHolder, position: Int) {
        holder.bind(discussions[position])
    }

    inner class DiscussionViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(discussion: Discussion) {
            with(itemView) {
                Picasso.get().load(PathConstant.HOST + discussion.product.image).fit()
                    .into(iv_product_image)
                tv_discussion_question.text = discussion.detail
                tv_product_name.text = discussion.product.name
                tv_join_discussion.setOnClickListener {
                    communicator.replyDiscussion(discussion.id)
                }
            }
        }
    }

    interface IDiscussionCommunicator {
        fun replyDiscussion(discussionId: Int)
    }
}