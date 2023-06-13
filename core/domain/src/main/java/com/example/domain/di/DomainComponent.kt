package com.example.domain.di

import com.example.domain.cart.Cart
import com.example.domain.cart.CartImpl
import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [CartModule::class])
interface DomainComponent : CartProvider {
    companion object {
        fun init(): CartProvider {
            return DaggerDomainComponent.create()
        }
    }
}

@Module
interface CartModule {
    @Binds
    fun provideCart(cart: CartImpl): Cart
}