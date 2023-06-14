package com.example.data.repository

import com.example.data.base.safeRequest
import com.example.data.mappers.toDish
import com.example.data.service.DishesService
import com.example.domain.model.Tag
import com.example.domain.repository.DishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DishRepositoryImpl @Inject constructor(
    private val service: DishesService
) : DishRepository {
    private val dispatcher = Dispatchers.IO

    override suspend fun fetchDishes() = withContext(dispatcher) {
        safeRequest {
            service.fetchDishes().dishes.map { it.toDish() }
        }
    }

    override suspend fun fetchDishesByTags(tags: List<Tag>) = withContext(dispatcher) {
        safeRequest {
            service.fetchDishes().dishes
                .filter { dish -> dish.tags.any { tag -> tags.firstOrNull { it.name == tag } != null } }
                .map { it.toDish() }
        }
    }
}