package com.example.domain.cart

import com.example.domain.model.CartItem
import com.example.domain.model.Dish
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartImpl @Inject constructor() : Cart {
    private val dispatcher = Dispatchers.Default
    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    override val items: Flow<List<CartItem>>
        get() = _items.asStateFlow().map { items -> items.sortedBy { it.date } }

    companion object {
        private const val MAX_COUNT = 100
    }
    override fun addItem(dish: Dish) {
        CoroutineScope(dispatcher).launch {
            val list = _items.value.toMutableList()
            val item = list.firstOrNull { it.dish == dish }
            if (item == null) {
                list.add(CartItem(dish))
            } else {
                if(item.count == MAX_COUNT) return@launch
                list.remove(item)
                list.add(item.copy(count = item.count + 1))
            }
            _items.emit(list)
        }
    }

    override fun removeItem(dish: Dish) {
        CoroutineScope(dispatcher).launch {
            val list = _items.value.toMutableList()
            val item = list.firstOrNull { it.dish == dish }
            if (item != null) {
                list.remove(item)
                if (item.count > 1) {
                    list.add(item.copy(count = item.count - 1))
                }
                _items.emit(list)
            }
        }
    }

    override suspend fun getTotalPrice() = withContext(dispatcher) {
        _items.value.sumOf { it.dish.price * it.count }
    }
}