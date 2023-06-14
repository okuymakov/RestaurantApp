package com.example.domain.di

import com.example.domain.repository.CategoryRepository
import com.example.domain.repository.DishRepository
import com.example.domain.repository.TagRepository

interface RepoProvider {
    fun provideCategoryRepository(): CategoryRepository
    fun provideDishRepository(): DishRepository
    fun provideTagRepository(): TagRepository
}
