package com.example.category.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Dish
import com.example.domain.network.Response
import com.example.domain.repository.DishRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class CategoryViewModel(
    private val repo: DishRepository
) : ViewModel() {
    private val _state = MutableStateFlow<Response<List<Dish>>>(Response.Success(emptyList()))
    val state get() = _state.asStateFlow()

    init {
       fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            val res = repo.fetchDishes()
            _state.emit(res)
        }
    }

    class Factory @Inject constructor(
        private val repo: Provider<DishRepository>
    ) : ViewModelProvider.Factory
    {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CategoryViewModel(repo.get()) as T
        }
    }
}