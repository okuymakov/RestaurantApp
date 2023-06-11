package com.example.data.mappers

import com.example.data.dto.CategoryDto
import com.example.domain.model.Category

fun CategoryDto.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}