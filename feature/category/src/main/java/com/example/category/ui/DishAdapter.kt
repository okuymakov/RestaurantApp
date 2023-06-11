package com.example.category.ui

import com.example.category.databinding.ItemDishBinding
import com.example.domain.model.Dish
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class DishAdapter(onClick: (Dish) -> Unit) :
    ListDelegationAdapter<List<Dish>>(adapterDelegate(onClick)) {
    fun updateData(items: List<Dish>) {
        this.items = items
        notifyItemRangeChanged(0, items.size)
    }
}

fun adapterDelegate(itemClickedListener: (Dish) -> Unit) =
    adapterDelegateViewBinding<Dish, Dish, ItemDishBinding>(
        { layoutInflater, root -> ItemDishBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.root.setOnClickListener {
            itemClickedListener(item)
        }
        bind {
            binding.text.text = item.name
            binding.productPreview.setImageUri(item.imageUrl)
        }
    }