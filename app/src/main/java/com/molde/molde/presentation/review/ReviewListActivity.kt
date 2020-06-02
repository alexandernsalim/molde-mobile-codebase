package com.molde.molde.presentation.review

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.molde.molde.BaseActivity
import com.molde.molde.R
import com.molde.molde.databinding.ActivityReviewListBinding
import com.molde.molde.util.invisible
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class ReviewListActivity : BaseActivity() {
    private lateinit var mBinding: ActivityReviewListBinding
    private val vModel = ReviewViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_review_list)

        mBinding.toolbar.title = "Daftar Ulasan"
        mBinding.toolbar.setNavigationOnClickListener { finish() }

        val adapter = ReviewAdapter()
        mBinding.rvReviews.layoutManager = LinearLayoutManager(this)
        mBinding.rvReviews.adapter = adapter

        vModel.userReviewsLiveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                mBinding.tvReviewsCond.invisible()
                adapter.setData(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getUserReviews()
    }

    private fun getUserReviews() {
        vModel.viewModelScope.launch {
            if (!vModel.getUserReviews()) {
                toast("Gagal mengunduh data")
            }
        }
    }

}
