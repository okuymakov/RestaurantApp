package com.example.category.di

import com.example.category.navigation.CategoryNavigator
import com.example.category.ui.CategoryFragment
import com.example.domain.di.RepoProvider
import dagger.Component

@Component(dependencies = [RepoProvider::class, CategoryNavigator::class])
abstract class CategoryComponent {
    abstract fun inject(fragment: CategoryFragment)

    companion object {
        private var component: CategoryComponent? = null
        fun init(repoProvider: RepoProvider, navigator: CategoryNavigator) {
            component =
                DaggerCategoryComponent.builder().deps(repoProvider).navigator(navigator).build()
        }

        fun get(): CategoryComponent {
            return component ?: throw RuntimeException("Component has not been initialized yet!")
        }
    }

    @Component.Builder
    interface Builder {
        fun deps(repoProvider: RepoProvider): Builder
        fun navigator(navigator: CategoryNavigator): Builder
        fun build(): CategoryComponent
    }
}