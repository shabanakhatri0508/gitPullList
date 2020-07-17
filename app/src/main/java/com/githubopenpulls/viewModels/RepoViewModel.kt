package com.githubopenpulls.viewModels

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.githubopenpulls.models.PullInfo
import com.githubopenpulls.webservices.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class RepoViewModel : BaseViewModel() {
    private var mOwnerName: ObservableField<String> = ObservableField()
    private var mRepoName: ObservableField<String> = ObservableField()
    private var subscription: CompositeDisposable = CompositeDisposable()
    private val mLoadingVisibility: MutableLiveData<Int> = MutableLiveData()
    private val errorMessage: MutableLiveData<String> = MutableLiveData()
    private var mPullList: MutableLiveData<ArrayList<PullInfo>> = MutableLiveData()

    @Inject
    lateinit var postApi: ApiInterface

    fun getObservableOwnerName(): ObservableField<String> {
        return mOwnerName
    }

    fun getLoadingVisibility(): MutableLiveData<Int> {
        return mLoadingVisibility
    }

    fun getPullList(): MutableLiveData<ArrayList<PullInfo>> {
        return mPullList
    }

    fun getObservableRepoName(): ObservableField<String> {
        return mRepoName
    }

    fun loadPulls() {
        subscription.add(
            postApi.getOpenPulls(mOwnerName.get()!!, mRepoName.get()!!, "open")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrievePostListStart() }
                .doOnTerminate { onRetrievePostListFinish() }
                .subscribeWith(object : DisposableObserver<ArrayList<PullInfo>>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: ArrayList<PullInfo>) {
                        onRetrievePollResultSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        onRetrievePostListError(e.message)
                    }
                })
        )
    }

    private fun onRetrievePollResultSuccess(t: ArrayList<PullInfo>) {
        this.mPullList.value = t
    }

    private fun onRetrievePostListStart() {
        mLoadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        mLoadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListError(error: String?) {
        errorMessage.value = error!!
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}