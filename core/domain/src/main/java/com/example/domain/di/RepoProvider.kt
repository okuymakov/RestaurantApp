package com.example.domain.di

import com.example.domain.repository.CategoryRepository
import com.example.domain.repository.DishRepository

interface RepoProvider {
    fun provideCategoryRepository(): CategoryRepository
    fun provideDishRepository(): DishRepository
}
