package com.example.domain.di

import com.example.domain.cart.Cart

interface CartProvider {
    fun provideCart() : Cart
}