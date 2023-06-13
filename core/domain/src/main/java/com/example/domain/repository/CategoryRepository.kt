package com.example.domain.repository

import com.example.domain.model.Category
import com.example.domain.network.Response

interface CategoryRepository {
    suspend fun fetchCategories(): Response<List<Category>>
}