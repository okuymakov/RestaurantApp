package com.example.home.di

import com.example.domain.di.RepoProvider
import com.example.home.navigation.HomeNavigator
import com.example.home.ui.HomeFragment
import dagger.Component

@Component(dependencies = [RepoProvider::class, HomeNavigator::class])
abstract class HomeComponent {
    abstract fun inject(fragment: HomeFragment)

    companion object {
        private var component: HomeComponent? = null
        fun init(repoProvider: RepoProvider, navigator: HomeNavigator) {
            component =
                DaggerHomeComponent.builder().deps(repoProvider).navigator(navigator).build()
        }

        fun get(): HomeComponent {
            return component ?: throw RuntimeException("Component has not been initialized yet!")
        }
    }

    @Component.Builder
    interface Builder {
        fun deps(repoProvider: RepoProvider): Builder
        fun navigator(navigator: HomeNavigator): Builder
        fun build(): HomeComponent
    }
}