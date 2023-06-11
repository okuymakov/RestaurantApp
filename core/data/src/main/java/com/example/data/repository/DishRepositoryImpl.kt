package com.example.data.repository

import com.example.data.mappers.toDish
import com.example.data.service.DishesService
import com.example.domain.model.Dish
import com.example.domain.repository.DishRepository
import javax.inject.Inject

class DishRepositoryImpl @Inject constructor(
    private val service: DishesService
) : DishRepository {

    override suspend fun fetchDishes(): List<Dish> {
        return service.fetchDishes().dishes.map { it.toDish() }
    }
}