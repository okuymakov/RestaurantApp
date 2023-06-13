package com.example.domain.cart

import com.example.domain.model.CartItem
import com.example.domain.model.Dish
import kotlinx.coroutines.flow.Flow

interface Cart {
    val items: Flow<List<CartItem>>
    fun addItem(dish: Dish)
    fun removeItem(dish: Dish)
    suspend fun getTotalPrice(): Int
}

