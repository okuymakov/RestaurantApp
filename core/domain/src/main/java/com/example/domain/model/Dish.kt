package com.example.domain.model

data class Dish(
    val id: String,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val imageUrl: String,
    val tags: List<String>
)