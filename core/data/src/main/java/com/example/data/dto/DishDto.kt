package com.example.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DishDto(
    val id: String,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("tegs")
    val tags: List<String>
)

@Serializable
data class DishesDto(
    val dishes: List<DishDto>
)