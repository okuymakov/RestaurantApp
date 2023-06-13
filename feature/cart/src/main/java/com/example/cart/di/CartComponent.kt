package com.example.cart.di

import com.example.cart.ui.CartFragment
import com.example.domain.di.CartProvider
import dagger.Component

@Component(dependencies = [CartProvider::class])
abstract class CartComponent {
    abstract fun inject(fragment: CartFragment)

    companion object {
        private var component: CartComponent? = null
        fun init(cartProvider: CartProvider) {
            component =
                DaggerCartComponent.builder().deps(cartProvider).build()
        }

        fun get(): CartComponent {
            return component ?: throw RuntimeException("Component has not been initialized yet!")
        }
    }

    @Component.Builder
    interface Builder {
        fun deps(cartProvider: CartProvider): Builder
        fun build(): CartComponent
    }
}