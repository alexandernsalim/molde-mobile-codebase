package com.molde.molde.model.entity

data class Discussion(
    val id: Int,
    val detail: String,
    val questionMaker: String,
    val lastReply: DiscussionReply?

)