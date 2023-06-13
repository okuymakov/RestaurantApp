package com.example.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Category
import com.example.domain.network.Response
import com.example.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class HomeViewModel(
    private val repo: CategoryRepository
) : ViewModel() {
    private val _state = MutableStateFlow<Response<List<Category>>>(Response.Success(emptyList()))
    val state get() = _state.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            val res = repo.fetchCategories()
            _state.emit(res)
        }
    }

    class Factory @Inject constructor(
        private val repo: Provider<CategoryRepository>
    ) : ViewModelProvider.Factory
    {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repo.get()) as T
        }
    }
}