package com.example.data.mappers

import com.example.data.dto.DishDto
import com.example.domain.model.Dish

fun DishDto.toDish(): Dish {
    return Dish(
        id = id,
        name = name,
        price = price,
        weight = weight,
        description = description,
        imageUrl = imageUrl,
        tags = tags
    )
}