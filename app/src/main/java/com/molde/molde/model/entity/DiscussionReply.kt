package com.molde.molde.model.entity

data class DiscussionReply(
    val id: Int,
    val message: String,
    val shopReplyUsername: String?,
    val shopUserReplyUsername: String?
)