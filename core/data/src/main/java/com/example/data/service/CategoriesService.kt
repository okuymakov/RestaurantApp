package com.example.data.service

import com.example.data.dto.CategoriesDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject


class CategoriesService @Inject constructor(private val client: HttpClient) {
    suspend fun fetchCategories(): CategoriesDto {
        return client.get("058729bd-1402-4578-88de-265481fd7d54").body()
    }
}