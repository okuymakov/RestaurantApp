package com.example.data.di

import com.example.data.repository.CategoryRepositoryImpl
import com.example.data.repository.DishRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun provideCategoryRepository(repo: CategoryRepositoryImpl): com.example.domain.repository.CategoryRepository

    @Binds
    fun provideDishRepository(repo: DishRepositoryImpl): com.example.domain.repository.DishRepository
}