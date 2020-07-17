package com.githubopenpulls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.githubopenpulls.databinding.ActivityPullListBinding
import com.githubopenpulls.models.PullInfo

class PullListActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPullListBinding
    private var mPullListAdapter: PullListAdapter? = null
    private var pullList: ArrayList<PullInfo> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pull_list)
        init()
    }

    private fun init() {
        if (intent.hasExtra("PullData")) {
            pullList = intent.getSerializableExtra("PullData") as ArrayList<PullInfo>
            if (pullList.size > 0) {
                mPullListAdapter = PullListAdapter()
                mBinding.recPulls.adapter = mPullListAdapter
                mPullListAdapter?.updateList(pullList)
            }
        }
    }
}