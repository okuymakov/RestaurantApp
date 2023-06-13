package com.example.domain.repository
import com.example.domain.model.Dish
import com.example.domain.network.Response

interface DishRepository {
    suspend fun fetchDishes(): Response<List<Dish>>
}