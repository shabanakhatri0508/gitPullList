package com.githubopenpulls.ui

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.githubopenpulls.R
import com.githubopenpulls.constants.AppConstants
import com.githubopenpulls.databinding.ActivityMainBinding
import com.githubopenpulls.databinding.AppLoadingDialogBinding
import com.githubopenpulls.models.PullInfo
import com.githubopenpulls.viewModels.RepoViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mRepoViewModel: RepoViewModel
    private var mPullList: ArrayList<PullInfo> = arrayListOf()
    private var mProgressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@MainActivity,
            R.layout.activity_main
        )
        init()
    }


    private fun init() {
        mRepoViewModel = ViewModelProvider(this@MainActivity).get(RepoViewModel::class.java)
        mBinding.data = mRepoViewModel
        mBinding.executePendingBindings()
        setObserver()
        mBinding.btnSubmit.setOnClickListener {
            validate()
        }
    }

    private fun validate() {
        if (mRepoViewModel.getObservableOwnerName().get().isNullOrEmpty()) {
            Toast.makeText(
                this@MainActivity,
                getString(R.string.please_enter_owner_name),
                Toast.LENGTH_SHORT
            )
                .show()

        } else if (mRepoViewModel.getObservableRepoName().get().isNullOrEmpty()) {
            Toast.makeText(
                this@MainActivity,
                getString(R.string.please_enter_repo_name),
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            getPulls()
        }
    }

    private fun getPulls() {
        mRepoViewModel.loadPulls()
    }

    private fun setObserver() {
        mRepoViewModel.getPullList().observe(this@MainActivity, Observer {
            if (it != null) {
                mPullList = it
                startActivity(
                    Intent(this@MainActivity, PullListActivity::class.java).putExtra(
                        AppConstants.pullData,
                        mPullList
                    )
                )
            }
        })
        mRepoViewModel.getLoadingVisibility().observe(this, Observer {
            if (it != null) {
                if (it == 1)
                    showProgress()
                else
                    hideProgress()
            }
        })
        mRepoViewModel.getErrorMessage().observe(this, Observer {
            Toast.makeText(
                this@MainActivity,
                getString(R.string.no_pul_request_found_with_given_info),
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    private fun showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = Dialog(this@MainActivity)
            mProgressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        // inflating and setting view of custom dialog
        val progressDialogBinding =
            AppLoadingDialogBinding.inflate(LayoutInflater.from(this@MainActivity), null, false)
        ObjectAnimator.ofFloat(progressDialogBinding.imageView2, View.ROTATION, 360f, 0f).apply {
            repeatCount = ObjectAnimator.INFINITE
            duration = 1500
            interpolator = LinearInterpolator()
            start()
        }
        mProgressDialog!!.setContentView(progressDialogBinding.root)

//      setting background of dialog as transparent
        val window = mProgressDialog!!.window
        window?.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent))
//      preventing outside touch and setting cancelable false
        mProgressDialog!!.setCancelable(false)
        mProgressDialog!!.setCanceledOnTouchOutside(false)
        mProgressDialog!!.show()
    }

    private fun hideProgress() {
        mProgressDialog?.run {
            if (isShowing) {
                dismiss()
            }
        }
    }
}