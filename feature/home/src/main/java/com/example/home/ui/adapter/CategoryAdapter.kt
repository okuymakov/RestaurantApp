package com.example.home.ui.adapter

import coil.load
import com.example.domain.model.Category
import com.example.home.databinding.ItemCategoryBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class CategoryAdapter(onClick: (Category) -> Unit) :
    ListDelegationAdapter<List<Category>>(adapterDelegate(onClick)) {
        fun updateData(items: List<Category>) {
            this.items = items
            notifyItemRangeChanged(0, items.size)
        }
    }

fun adapterDelegate(itemClickedListener: (Category) -> Unit) =
    adapterDelegateViewBinding<Category, Category, ItemCategoryBinding>(
        { layoutInflater, root -> ItemCategoryBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.root.setOnClickListener {
            itemClickedListener(item)
        }
        bind {
            binding.text.text = item.name
            binding.image.load(item.imageUrl)
        }
    }