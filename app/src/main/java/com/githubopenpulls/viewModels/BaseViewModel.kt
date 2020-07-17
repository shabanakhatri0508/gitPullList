package com.githubopenpulls.viewModels

import androidx.lifecycle.ViewModel
import com.githubopenpulls.injections.component.DaggerViewModelInjector
import com.githubopenpulls.injections.component.ViewModelInjector
import com.githubopenpulls.module.NetworkModule

open class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is RepoViewModel -> injector.inject(this)
        }
    }

}