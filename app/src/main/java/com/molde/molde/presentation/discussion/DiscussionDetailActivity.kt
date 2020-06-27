package com.molde.molde.presentation.discussion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.messaging.FirebaseMessaging
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityDiscussionDetailBinding
import com.molde.molde.model.entity.DiscussionReply
import com.molde.molde.service.FCMService
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class DiscussionDetailActivity : BaseActivity() {
    private lateinit var mBinding: ActivityDiscussionDetailBinding
    private lateinit var receiver: BroadcastReceiver
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

        subscribe()

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver, IntentFilter(FCMService.DISCUSSION))

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

    override fun onDestroy() {
        super.onDestroy()
        unsubscribe()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }

    private fun subscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/discussionReply")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.discussion_topic_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.discussion_topic_subscribed_err)
                }
                Log.i("FCM", msg)
            }

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == FCMService.DISCUSSION) {
                    val discussionId = intent.getIntExtra("discussionId", 0)
                    val shopUsername = intent.getStringExtra("shopUsername")
                    val shopUserUsername = intent.getStringExtra("shopUserUsername")
                    val message = intent.getStringExtra("message")

                    if (this@DiscussionDetailActivity.discussionId == discussionId) {
                        adapter.setItem(
                            DiscussionReply(
                                discussionId,
                                message,
                                shopUsername,
                                shopUserUsername
                            )
                        )
                    }
                }
            }
        }
    }

    private fun unsubscribe() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("/topics/discussionReply")
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.i("FCM", "Failed to unsubscribe discussion topic")
                } else {
                    Log.i("FCM", "Discussion topic unsubscribed")
                }
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