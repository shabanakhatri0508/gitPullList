package com.githubopenpulls.injections.component

import com.githubopenpulls.module.NetworkModule
import com.githubopenpulls.viewModels.RepoViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    fun inject(repoViewModel: RepoViewModel)
    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}