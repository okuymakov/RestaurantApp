package com.example.product.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.cart.Cart
import com.example.domain.model.Dish
import javax.inject.Inject
import javax.inject.Provider

class ProductViewModel(private val cart: Cart) : ViewModel() {

    fun addToCart(dish: Dish) {
        cart.addItem(dish)
    }

    class Factory @Inject constructor(
        private val cart: Provider<Cart>
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProductViewModel(cart.get()) as T
        }
    }
}