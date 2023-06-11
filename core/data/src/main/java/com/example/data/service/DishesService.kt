package com.example.data.service

import com.example.data.dto.DishesDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject


class DishesService @Inject constructor(private val client: HttpClient) {
    suspend fun fetchDishes(): DishesDto {
        return client.get("aba7ecaa-0a70-453b-b62d-0e326c859b3b").body()
    }
}