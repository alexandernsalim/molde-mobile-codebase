package com.molde.molde.presentation.discussion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molde.molde.R
import com.molde.molde.model.entity.DiscussionReply
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_message.*

class DiscussionDetailAdapter :
    RecyclerView.Adapter<DiscussionDetailAdapter.DiscussionDetailViewHolder>() {
    private val discussionReplies: MutableList<DiscussionReply> = mutableListOf()

    fun setData(replies: List<DiscussionReply>) {
        this.discussionReplies.clear()
        this.discussionReplies.addAll(replies)
        notifyDataSetChanged()
    }

    fun setItem(reply: DiscussionReply) {
        this.discussionReplies.add(reply)
        notifyItemInserted(discussionReplies.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DiscussionDetailViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
    )

    override fun getItemCount() = discussionReplies.size

    override fun onBindViewHolder(holderDetail: DiscussionDetailViewHolder, position: Int) {
        holderDetail.bind(discussionReplies[position])
    }

    inner class DiscussionDetailViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(reply: DiscussionReply) {
            tv_message_owner.text = reply.shopUserReplyUsername ?: reply.shopReplyUsername
            tv_message.text = reply.message
        }
    }
}