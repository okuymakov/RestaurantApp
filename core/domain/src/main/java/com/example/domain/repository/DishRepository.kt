package com.example.domain.repository
import com.example.domain.model.Dish

interface DishRepository {
    suspend fun fetchDishes(): List<Dish>
}