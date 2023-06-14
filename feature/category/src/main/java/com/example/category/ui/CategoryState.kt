package com.example.category.ui

import com.example.domain.model.Dish

data class CategoryState(
    val dishes: List<Dish> = emptyList(),
    val tags: List<SelectableTag> = emptyList(),
    val isLoading: Boolean = true,
    val hasNoInternet: Boolean = false,
    val hasUnknownError: Boolean = false,
    val hasApiError: Boolean = false
)