package com.molde.molde.presentation.discussion

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityCreateDiscussionBinding
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class CreateDiscussionActivity : BaseActivity() {
    private lateinit var mBinding: ActivityCreateDiscussionBinding
    private val vModel = DiscussionViewModel()

    private var discussionId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_discussion)
        setSupportActionBar(mBinding.toolbar)
        mBinding.toolbar.title = "Buat Diskusi Baru"
        mBinding.toolbar.setNavigationOnClickListener { finish() }

        discussionId = intent.getIntExtra("PRODUCT_ID",0)

        mBinding.btCreateDiscussion.setOnClickListener {
            var isError = false
            val question = mBinding.etDiscussionQuestion.text.toString()

            if (question.isEmpty()) {
                mBinding.etDiscussionQuestion.error = "Pertanyaan tidak dapat dikosongkan"
                isError = true
            }

            if (!isError) {
                discussionId?.let { createDiscussion(it, question) }
            }
        }
    }

    private fun createDiscussion(productId: Int, detail: String) {
        vModel.viewModelScope.launch {
            if (!vModel.createDiscussion(productId, detail)) {
                toast("Gagal memuat data")
            }
        }

        vModel.createDiscussionLiveData.observe(this, Observer {
            if (it != null) {
                finish()
                toast("Diskusi berhasil dibuat")
            }
        })
    }

}