package com.molde.molde.presentation.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.molde.molde.R
import com.molde.molde.databinding.FragmentProductDiscussionBinding
import com.molde.molde.presentation.discussion.CreateDiscussionActivity
import com.molde.molde.presentation.discussion.DiscussionDetailActivity
import com.molde.molde.util.invisible
import kotlinx.android.synthetic.main.fragment_product_discussion.*
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class ProductDiscussionFragment : Fragment(), ProductDiscussionAdapter.IDiscussionCommunicator {
    private lateinit var mBinding: FragmentProductDiscussionBinding
    private val vModel = ProductDetailViewModel()
    private val adapter = ProductDiscussionAdapter(this)

    private var productId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product_discussion,
            container,
            false
        )

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productId = arguments?.getInt(ProductDetailActivity.EXTRA_PRODUCT_ID)
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        mBinding.rvDiscussions.layoutManager = LinearLayoutManager(context)
        mBinding.rvDiscussions.addItemDecoration(itemDecoration)
        mBinding.rvDiscussions.adapter = adapter

        mBinding.btAddDiscussion.setOnClickListener {
            startActivity<CreateDiscussionActivity>(
                "PRODUCT_ID" to productId
            )
        }

        vModel.discussionsLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                tv_discussion_cond.invisible()
                adapter.setData(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        productId?.let { getDiscussions(it) }
    }

    override fun replyDiscussion(discussionId: Int) {
        startActivity<DiscussionDetailActivity>(
            DiscussionDetailActivity.EXTRA_DISCUSSION to discussionId
        )
    }

    private fun getDiscussions(productId: Int) {
        vModel.viewModelScope.launch {
            if (!vModel.getDiscussions(productId)) {
                toast("Gagal memuat data")
            }
        }
    }

}