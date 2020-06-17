package com.molde.molde.presentation.discussion

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityDiscussionDetailBinding
import com.molde.molde.util.SharedPreferencesManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import ua.naiksoftware.stomp.LifecycleEvent
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompHeader
import ua.naiksoftware.stomp.client.StompClient
import ua.naiksoftware.stomp.client.StompMessage

class DiscussionDetailActivity : BaseActivity() {
    private lateinit var mBinding: ActivityDiscussionDetailBinding
    private lateinit var stompClient: StompClient
    private val vModel = DiscussionViewModel()
    private val sharedPreferencesManager = SharedPreferencesManager()
    private val adapter = DiscussionDetailAdapter()
    private val compositeDisposable = CompositeDisposable()

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

        connect()

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

//            if (!isError) discussionId?.let { replyDiscussion(it, reply) }
            if (!isError) discussionId?.let { replyDiscussionWs(it, reply) }
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

    override fun onDestroy() {
        super.onDestroy()
        disconnect()
    }

    private fun connect() {
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:9000/molde/ws/websocket")
        stompClient.connect()

        compositeDisposable.add(stompClient.lifecycle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it.type) {
                    LifecycleEvent.Type.OPENED -> {
                        Log.i("WS", "Websocket connection opened")
                    }
                    LifecycleEvent.Type.ERROR -> {
                        Log.e("WS", "Failed to connect websocket, error: ${it.exception}")
                    }
                    LifecycleEvent.Type.CLOSED -> {
                        Log.i("WS", "Websocket connection closed")
                    }
                }
            }
        )

        compositeDisposable.add(
            stompClient.topic("/topic/discussion")
                .subscribe {
                    Log.i("WS", "New reply received: ${it.payload}")
                }
        )
    }

    private fun disconnect() {
        stompClient.disconnect()
        compositeDisposable.clear()
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

    private fun replyDiscussionWs(discussionId: Int, detail: String) {
        val gson = Gson()
        val headers: MutableList<StompHeader> = mutableListOf()
        headers.add(StompHeader("Authorization", sharedPreferencesManager.getToken()))
        val message = StompMessage("/topic/${discussionId}/reply", headers, gson.toJson(detail))
        compositeDisposable.add(
            stompClient.send(message)
                .doFinally {
                    Log.e("WS", "Sended")
                }
                .doOnSubscribe {
                    Log.i("WS", "Subscribed")
                }
                .subscribe()
        )
    }

}