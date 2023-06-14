package com.example.product.di

import com.example.domain.di.CartProvider
import com.example.product.ui.ProductDialog
import dagger.Component

@Component(dependencies = [CartProvider::class])
abstract class ProductComponent {
    abstract fun inject(fragment: ProductDialog)

    companion object {
        private var component: ProductComponent? = null
        fun init(cartProvider: CartProvider) {
            component =
                DaggerProductComponent.builder().deps(cartProvider).build()
        }

        fun get(): ProductComponent {
            return component ?: throw RuntimeException("Component has not been initialized yet!")
        }
    }

    @Component.Builder
    interface Builder {
        fun deps(cartProvider: CartProvider): Builder
        fun build(): ProductComponent
    }
}