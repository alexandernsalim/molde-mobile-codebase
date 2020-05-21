package com.molde.molde.presentation.discussion

import com.molde.molde.model.entity.Discussion
import com.molde.molde.model.entity.DiscussionReply
import com.molde.molde.model.response.DiscussionDetailResponse
import com.molde.molde.model.response.MoldeResponse
import com.molde.molde.network.RetrofitClient
import com.molde.molde.util.SharedPreferencesManager
import okhttp3.MultipartBody

class DiscussionRepository {
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val discussionService = RetrofitClient.discussionClient()

    suspend fun getDiscussionDetail(discussionId: Int): MoldeResponse<DiscussionDetailResponse> {
        return discussionService.getDiscussion(sharedPreferencesManager.getToken(), discussionId)
    }

    suspend fun createDiscussion(
        productId: Int,
        detail: String
    ): MoldeResponse<Discussion> {
        val body = MultipartBody.Builder()
            .addFormDataPart("detail", detail)
            .build()

        return discussionService.createDiscussion(
            sharedPreferencesManager.getToken(),
            productId,
            body
        )
    }

    suspend fun replyDiscussion(discussionId: Int, detail: String): MoldeResponse<DiscussionReply> {
        val body = MultipartBody.Builder()
            .addFormDataPart("detail", detail)
            .build()

        return discussionService.replyDiscussion(
            sharedPreferencesManager.getToken(),
            discussionId,
            body
        )
    }

}