package com.example.category.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.recyclerview.widget.DiffUtil
import com.example.category.databinding.ItemTagBinding
import com.example.category.ui.SelectableTag
import com.example.core.ui.extensions.color
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class TagAdapter(
    onClick: (SelectableTag) -> Unit
) : AsyncListDifferDelegationAdapter<SelectableTag>(TagCallback(), adapterDelegate(onClick)) {

    class TagCallback : DiffUtil.ItemCallback<SelectableTag>() {
        override fun areItemsTheSame(oldItem: SelectableTag, newItem: SelectableTag): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: SelectableTag, newItem: SelectableTag): Boolean {
            return oldItem == newItem
        }
    }
}

fun adapterDelegate(onClick: (SelectableTag) -> Unit) =
    adapterDelegateViewBinding<SelectableTag, SelectableTag, ItemTagBinding>(
        { layoutInflater, root -> ItemTagBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.root.setOnClickListener {
            onClick(item)
        }
        bind {
            binding.tagName.text = item.name
            if (item.selected) {
                binding.root.backgroundTintList =
                    ColorStateList.valueOf(context.color(com.example.core.ui.R.color.blue))
                binding.tagName.setTextColor(Color.WHITE)
            } else {
                binding.root.backgroundTintList =
                    ColorStateList.valueOf(context.color(com.example.core.ui.R.color.gray_white))
                binding.tagName.setTextColor(Color.BLACK)
            }
        }
    }