package com.example.cart.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cart.R
import com.example.cart.databinding.FragmentCartBinding
import com.example.cart.di.CartComponent
import com.example.cart.ui.adapter.CartAdapter
import com.example.core.ui.extensions.launchOnStarted
import com.example.core.ui.viewbinding.viewBinding
import com.example.core.ui.views.RecyclerStateLayout
import com.example.domain.model.CartItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartFragment : Fragment(R.layout.fragment_cart) {
    @Inject
    internal lateinit var viewModelFactory: CartViewModel.Factory
    private val viewModel: CartViewModel by viewModels { viewModelFactory }
    private val adapter: CartAdapter by lazy { CartAdapter(::onMinusClick, ::onPlusClick) }
    private val binding by viewBinding(FragmentCartBinding::bind)

    private fun onMinusClick(item: CartItem) {
        viewModel.removeFromCart(item.dish)
    }

    private fun onPlusClick(item: CartItem) {
        viewModel.addToCart(item.dish)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        CartComponent.get().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cartItems.apply {
            adapter = this@CartFragment.adapter
        }
        launchOnStarted {
            viewModel.cartItems.collect { items ->
                if (items.isEmpty()) {
                    binding.listState.updateState(RecyclerStateLayout.State.Empty)
                    binding.payBtn.visibility = View.GONE
                } else {
                    binding.listState.updateState(RecyclerStateLayout.State.Success)
                    binding.payBtn.visibility = View.VISIBLE
                    viewLifecycleOwner.lifecycleScope.launch {
                        binding.payBtn.text =
                            resources.getString(R.string.btn_pay, viewModel.getTotalPrice())
                    }
                    adapter.items = items
                }
            }
        }
    }
}