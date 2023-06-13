package com.example.restaurantapp.di

import com.example.cart.di.CartComponent
import com.example.category.di.CategoryComponent
import com.example.category.navigation.CategoryNavigator
import com.example.data.di.DataComponent
import com.example.domain.di.DomainComponent
import com.example.home.di.HomeComponent
import com.example.home.navigation.HomeNavigator
import com.example.product.di.ProductComponent
import dagger.Component
import javax.inject.Scope

@AppScope
@Component
interface AppComponent {

    companion object {
        fun init(): AppComponent {
            return DaggerAppComponent.create()
        }

        fun provideDeps(homeNavigator: HomeNavigator, categoryNavigator: CategoryNavigator) {
            val repoProvider = DataComponent.init()
            val cartProvider = DomainComponent.init()
            HomeComponent.init(repoProvider, homeNavigator)
            CategoryComponent.init(repoProvider, categoryNavigator)
            CartComponent.init(cartProvider)
            ProductComponent.init(cartProvider)
        }
    }
}

@Scope
annotation class AppScope