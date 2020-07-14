package com.molde.molde.presentation.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.molde.molde.R
import com.molde.molde.databinding.ActivityReviewBinding
import com.molde.molde.model.request.ReviewRequest
import com.molde.molde.presentation.order_detail.OrderDetailActivity
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class ReviewActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityReviewBinding
    private val vModel = ReviewViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_review)

        mBinding.toolbar.title = "Buat Review"
        mBinding.toolbar.setNavigationOnClickListener { finish() }

        val orderItemId = intent.getIntExtra(OrderDetailActivity.EXTRA_ORDER_ITEM_ID, 0)
        val productId = intent.getIntExtra(OrderDetailActivity.EXTRA_PRODUCT_ID, 0)

        mBinding.btSubmit.setOnClickListener {
            val title = mBinding.etTitle.text.toString()
            val description = mBinding.etDescription.text.toString()
            val rating = mBinding.rbService.rating.toString()
            var isError = false

            if (title.isEmpty()) {
                mBinding.etTitle.error = "Judul harus diisi"
                isError = true
            }

            if (description.isEmpty()) {
                mBinding.etDescription.error = "Deskripsi harus diisi"
                isError = true
            }

            if (rating.isEmpty()) {
                isError = true
            }

            if (!isError) {
                val reviewRequest = ReviewRequest(
                    productId,
                    title,
                    description,
                    rating
                )

                createReview(orderItemId, reviewRequest)
            }
        }

        vModel.createLiveData.observe(this, Observer {
            finish()
            toast("Review produk berhasil")
        })
    }

    private fun createReview(productId: Int, reviewRequest: ReviewRequest) {
        vModel.viewModelScope.launch {
            if (!vModel.createReview(productId, reviewRequest)) {
                toast("Review produk gagal, coba kembali")
            }
        }
    }

}
