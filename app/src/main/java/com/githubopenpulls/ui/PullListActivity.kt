package com.githubopenpulls.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.githubopenpulls.R
import com.githubopenpulls.constants.AppConstants
import com.githubopenpulls.databinding.ActivityPullListBinding
import com.githubopenpulls.models.PullInfo

class PullListActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPullListBinding
    private var mPullListAdapter: PullListAdapter? = null
    private var mPullList: ArrayList<PullInfo> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@PullListActivity,
            R.layout.activity_pull_list
        )
        init()
    }

    private fun init() {
        if (intent.hasExtra(AppConstants.pullData)) {
            mPullList = intent.getSerializableExtra(AppConstants.pullData) as ArrayList<PullInfo>
            if (mPullList.size > 0) {
                mPullListAdapter = PullListAdapter()
                mBinding.recPulls.adapter = mPullListAdapter
                mPullListAdapter?.updateList(mPullList)
            }
        }
    }
}