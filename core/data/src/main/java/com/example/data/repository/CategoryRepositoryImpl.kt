package com.example.data.repository

import com.example.data.mappers.toCategory
import com.example.data.service.CategoriesService
import com.example.domain.model.Category
import com.example.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val service: CategoriesService
) : CategoryRepository {

    override suspend fun fetchCategories(): List<Category> {
        return service.fetchCategories().categories.map { it.toCategory() }
    }
}