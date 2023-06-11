package com.example.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Long,
    val name: String,
    @SerialName("image_url")
    val imageUrl: String
)

@Serializable
data class CategoriesDto(
    @SerialName("сategories")
    val categories: List<CategoryDto>
)