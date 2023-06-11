package com.example.restaurantapp.di

import com.example.category.di.CategoryComponent
import com.example.data.di.DataComponent
import com.example.home.di.HomeComponent
import com.example.home.navigation.HomeNavigator
import dagger.Component
import javax.inject.Scope

@AppScope
@Component
interface AppComponent {

    companion object {
        fun init(): AppComponent {
            return DaggerAppComponent.create()
        }

        fun provideDeps(navigator: HomeNavigator) {
            val repoProvider = DataComponent.init()
            HomeComponent.init(repoProvider, navigator)
            CategoryComponent.init(repoProvider)
        }
    }
}

@Scope
annotation class AppScope