package com.example.category.navigation

import com.example.domain.model.Dish

interface CategoryNavigator {
    fun navigateToProduct(dish: Dish)
}