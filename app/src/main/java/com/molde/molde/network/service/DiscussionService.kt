package com.molde.molde.network.service

import com.molde.molde.model.entity.Discussion
import com.molde.molde.model.entity.DiscussionReply
import com.molde.molde.model.response.DiscussionDetailResponse
import com.molde.molde.model.response.MoldeResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface DiscussionService {

    @GET("{discussionId}")
    suspend fun getDiscussion(
        @Header("Authorization") auth: String,
        @Path("discussionId") discussionId: Int
    ): MoldeResponse<DiscussionDetailResponse>

    @POST("{productId}")
    suspend fun createDiscussion(
        @Header("Authorization") auth: String,
        @Path("productId") productId: Int,
        @Body request: RequestBody
    ): MoldeResponse<Discussion>

    @POST("{discussionId}/reply")
    suspend fun replyDiscussion(
        @Header("Authorization") auth: String,
        @Path("discussionId") discussionId: Int,
        @Body request: RequestBody
    ): MoldeResponse<DiscussionReply>

}
