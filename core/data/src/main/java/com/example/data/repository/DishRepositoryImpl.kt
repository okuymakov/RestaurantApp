package com.example.data.repository

import com.example.data.base.safeRequest
import com.example.data.mappers.toDish
import com.example.data.service.DishesService
import com.example.domain.model.Dish
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
}