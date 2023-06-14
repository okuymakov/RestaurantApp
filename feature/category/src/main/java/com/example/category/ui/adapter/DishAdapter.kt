package com.example.category.ui.adapter

import android.annotation.SuppressLint
import com.example.category.databinding.ItemDishBinding
import com.example.domain.model.Dish
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class DishAdapter(onClick: (Dish) -> Unit) :
    ListDelegationAdapter<List<Dish>>(adapterDelegate(onClick)) {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(items: List<Dish>) {
        if (this.items == items) return
        this.items = items
        notifyDataSetChanged()
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
            binding.dishName.text = item.name
            binding.dishPreview.setImageUri(item.imageUrl)
        }
    }