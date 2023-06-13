package com.example.cart.ui.adapter

import android.text.SpannableStringBuilder
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.text.color
import androidx.recyclerview.widget.DiffUtil
import com.example.cart.R
import com.example.cart.databinding.ItemCartBinding
import com.example.core.ui.extensions.color
import com.example.domain.model.CartItem
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class CartAdapter(
    onMinusClick: (CartItem) -> Unit,
    onPlusClick: (CartItem) -> Unit
) : AsyncListDifferDelegationAdapter<CartItem>(
    CartItemCallback(),
    adapterDelegate(onMinusClick, onPlusClick)
) {

    class CartItemCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.dish.id == newItem.dish.id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: CartItem, newItem: CartItem): Any? {
            return if (oldItem.count != newItem.count) newItem.count else null
        }
    }
}

fun adapterDelegate(
    onMinusClick: (CartItem) -> Unit,
    onPlusClick: (CartItem) -> Unit
) = adapterDelegateViewBinding<CartItem, CartItem, ItemCartBinding>(
    { layoutInflater, root -> ItemCartBinding.inflate(layoutInflater, root, false) }
) {
    binding.minusBtn.setOnClickListener {
        onMinusClick(item)
    }
    binding.plusBtn.setOnClickListener {
        onPlusClick(item)
    }
    bind { payloads: List<Any?> ->
        if (payloads.isNotEmpty() && payloads[0] != null) {
            binding.dishCount.text = payloads[0].toString()
        } else {
            binding.dishName.text = item.dish.name
            binding.dishPreview.setImageUri(item.dish.imageUrl)
            val spannable = SpannableStringBuilder()
                .append(context.resources.getString(R.string.price, item.dish.price))
                .color(context.color(com.example.core.ui.R.color.black_40)) {
                    append(" · ${context.resources.getString(R.string.weight, item.dish.weight)}")
                }
            binding.dishPriceAndWeight.text = spannable
            binding.dishCount.text = item.count.toString()
        }
    }
}