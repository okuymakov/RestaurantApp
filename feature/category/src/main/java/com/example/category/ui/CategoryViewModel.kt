package com.example.category.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Tag
import com.example.domain.network.Response
import com.example.domain.repository.DishRepository
import com.example.domain.repository.TagRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class CategoryViewModel(
    private val dishRepo: DishRepository,
    private val tagRepo: TagRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(CategoryState())
    val state get() = _state.asStateFlow()

    init {
        fetchTags()
    }

    fun fetchDishes() {
        viewModelScope.launch {
            _state.emit(_state.value.copy(isLoading = true))
            val tags = _state.value.tags.filter { it.selected }.map { Tag(it.name) }
            when (val res = dishRepo.fetchDishesByTags(tags)) {
                is Response.Failure.ApiError -> _state.emit(
                    _state.value.copy(hasApiError = true, isLoading = false)
                )
                is Response.Failure.NoInternetError -> _state.emit(
                    _state.value.copy(hasNoInternet = true, isLoading = false)
                )
                is Response.Failure.UnknownError -> _state.emit(
                    _state.value.copy(hasUnknownError = true, isLoading = false)
                )
                is Response.Success -> _state.emit(
                    _state.value.copy(dishes = res.data, isLoading = false)
                )
            }
        }
    }

    fun selectTag(tag: SelectableTag) {
        viewModelScope.launch {
            val tags = _state.value.tags.map { t ->
                if (t.name == tag.name) t.copy(selected = !t.selected)
                else t
            }
            _state.emit(_state.value.copy(tags = tags))
            fetchDishes()
        }
    }

    private fun fetchTags() {
        viewModelScope.launch {
            val tags = tagRepo.fetchTags().mapIndexed { index, tag ->
                if (index == 0) {
                    SelectableTag(tag.name, true)
                } else SelectableTag(tag.name)
            }
            _state.emit(_state.value.copy(tags = tags))
        }
    }

    class Factory @Inject constructor(
        private val dishRepo: Provider<DishRepository>,
        private val tagRepo: Provider<TagRepository>
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CategoryViewModel(dishRepo.get(), tagRepo.get()) as T
        }
    }
}