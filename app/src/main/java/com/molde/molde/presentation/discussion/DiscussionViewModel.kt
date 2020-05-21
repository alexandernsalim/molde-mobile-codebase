package com.molde.molde.presentation.discussion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.molde.molde.model.constant.ResponseCode
import com.molde.molde.model.entity.Discussion
import com.molde.molde.model.entity.DiscussionReply
import com.molde.molde.model.response.DiscussionDetailResponse

class DiscussionViewModel : ViewModel() {
    private val repository = DiscussionRepository()

    val createDiscussionLiveData: MutableLiveData<Discussion> = MutableLiveData()
    val replyDiscussionLiveData: MutableLiveData<DiscussionReply> = MutableLiveData()
    val discussionDetailLiveData: MutableLiveData<DiscussionDetailResponse> = MutableLiveData()

    suspend fun getDiscussionDetail(discussionId: Int): Boolean {
        val response = repository.getDiscussionDetail(discussionId)

        return if (response.code == ResponseCode.SUCCESS) {
            discussionDetailLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun createDiscussion(productId: Int, detail: String): Boolean {
        val response = repository.createDiscussion(productId, detail)

        return if (response.code == ResponseCode.SUCCESS) {
            createDiscussionLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

    suspend fun replyDiscussion(discussionId: Int, detail: String): Boolean {
        val response = repository.replyDiscussion(discussionId, detail)

        return if (response.code == ResponseCode.SUCCESS) {
            replyDiscussionLiveData.postValue(response.data)
            true
        } else {
            false
        }
    }

}