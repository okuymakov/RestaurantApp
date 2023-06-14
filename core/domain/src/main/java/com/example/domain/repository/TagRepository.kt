package com.example.domain.repository

import com.example.domain.model.Tag

interface TagRepository {
    suspend fun fetchTags(): List<Tag>
}