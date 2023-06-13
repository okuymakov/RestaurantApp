package com.example.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.cart.Cart
import com.example.domain.model.Dish
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

class CartViewModel(private val cart: Cart) : ViewModel() {
    val cartItems =
        cart.items.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun addToCart(dish: Dish) = cart.addItem(dish)
    fun removeFromCart(dish: Dish) = cart.removeItem(dish)
    suspend fun getTotalPrice() = cart.getTotalPrice()

    class Factory @Inject constructor(
        private val cart: Provider<Cart>
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CartViewModel(cart.get()) as T
        }
    }
}