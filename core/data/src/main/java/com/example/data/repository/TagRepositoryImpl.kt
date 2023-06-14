package com.example.data.repository

import com.example.domain.model.Tag
import com.example.domain.repository.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor() : TagRepository {
    override suspend fun fetchTags(): List<Tag> {
        return listOf(Tag("Все меню"), Tag("С рисом"), Tag("Салаты"), Tag("С рыбой"))
    }
}