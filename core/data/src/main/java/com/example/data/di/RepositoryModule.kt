package com.example.data.di

import com.example.data.repository.CategoryRepositoryImpl
import com.example.data.repository.DishRepositoryImpl
import com.example.domain.repository.CategoryRepository
import com.example.domain.repository.DishRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun provideCategoryRepository(repo: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun provideDishRepository(repo: DishRepositoryImpl): DishRepository
}