package com.molde.molde.presentation.discussion

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityDiscussionDetailBinding
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class DiscussionDetailActivity : BaseActivity() {
    private lateinit var mBinding: ActivityDiscussionDetailBinding
    private val vModel = DiscussionViewModel()
    private val adapter = DiscussionDetailAdapter()

    private var discussionId: Int? = null

    companion object {
        const val EXTRA_DISCUSSION = "DISCUSSION_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_discussion_detail)
        setSupportActionBar(mBinding.toolbar)
        mBinding.toolbar.title = "Diskusi"
        mBinding.toolbar.setNavigationOnClickListener { finish() }

        discussionId = intent.getIntExtra(EXTRA_DISCUSSION, 0)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)

        mBinding.rvMessages.layoutManager = LinearLayoutManager(this)
        mBinding.rvMessages.addItemDecoration(itemDecoration)
        mBinding.rvMessages.adapter = adapter

        mBinding.btSend.setOnClickListener {
            var isError = false
            val reply = mBinding.etDiscussionReply.text.toString()

            if (reply.isEmpty()) {
                isError = true
            }

            if (!isError) discussionId?.let { replyDiscussion(it, reply) }
        }

        vModel.discussionDetailLiveData.observe(this, Observer {
            if (it != null) {
                mBinding.tvDiscussionQuestion.text = it.question
                adapter.setData(it.responses)
            }
        })

        vModel.replyDiscussionLiveData.observe(this, Observer {
            if (it != null) {
                adapter.setItem(it)
                mBinding.etDiscussionReply.setText("")
                mBinding.etDiscussionReply.clearFocus()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        discussionId?.let {
            loadDetail(it)
        }
    }

    private fun loadDetail(discussionId: Int) {
        vModel.viewModelScope.launch {
            if (!vModel.getDiscussionDetail(discussionId)) {
                toast("Gagal memuat data")
            }
        }
    }

    private fun replyDiscussion(discussionId: Int, detail: String) {
        vModel.viewModelScope.launch {
            if (!vModel.replyDiscussion(discussionId, detail)) {
                toast("Gagal memuat data")
            }
        }
    }

}