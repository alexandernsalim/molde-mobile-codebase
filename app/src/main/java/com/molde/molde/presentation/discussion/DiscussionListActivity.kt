package com.molde.molde.presentation.discussion

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityDiscussionListBinding
import com.molde.molde.util.invisible
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class DiscussionListActivity : BaseActivity(), DiscussionAdapter.IDiscussionCommunicator {
    private lateinit var mBinding: ActivityDiscussionListBinding
    private val vModel = DiscussionViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_discussion_list)

        mBinding.toolbar.title = "Daftar Diskusi"
        mBinding.toolbar.setNavigationOnClickListener { finish() }

        val adapter = DiscussionAdapter(this)
        mBinding.rvDiscussions.layoutManager = LinearLayoutManager(this)
        mBinding.rvDiscussions.adapter = adapter

        vModel.userDiscussionsLiveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                mBinding.tvDiscussionCond.invisible()
                adapter.setData(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getDiscussions()
    }

    private fun getDiscussions() {
        vModel.viewModelScope.launch {
            if (!vModel.getDiscussions()) {
                toast("Gagal mengunduh data")
            }
        }
    }

    override fun replyDiscussion(discussionId: Int) {
        startActivity<DiscussionDetailActivity>(
            DiscussionDetailActivity.EXTRA_DISCUSSION to discussionId
        )
    }

}
