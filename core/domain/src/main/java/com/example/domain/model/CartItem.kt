package com.example.domain.model

import java.util.*

data class CartItem(val dish: Dish, val count: Int = 1, val date: Date = Date())