package com.molde.molde.model.response

import com.molde.molde.model.entity.DiscussionReply

data class DiscussionDetailResponse(
    val id: Int,
    val question: String,
    val questionOwner: String,
    val responses: List<DiscussionReply>
)