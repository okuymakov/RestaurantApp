package com.example.data.repository

import com.example.data.base.safeRequest
import com.example.data.mappers.toCategory
import com.example.data.mappers.toDish
import com.example.data.service.CategoriesService
import com.example.domain.model.Category
import com.example.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val service: CategoriesService
) : CategoryRepository {
    private val dispatcher = Dispatchers.IO
    override suspend fun fetchCategories() = withContext(dispatcher) {
        safeRequest {
            service.fetchCategories().categories.map { it.toCategory() }
        }
    }

}