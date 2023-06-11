package com.example.category.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.category.R
import com.example.category.di.CategoryComponent
import com.example.domain.model.Dish
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryFragment : Fragment(R.layout.fragment_category) {
    @Inject
    internal lateinit var viewModelFactory: CategoryViewModel.Factory
    private val viewModel: CategoryViewModel by viewModels { viewModelFactory }
    private val adapter: DishAdapter by lazy { DishAdapter(::onClick) }

    private fun onClick(dish: Dish) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        CategoryComponent.get().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.dishes).apply {
            adapter = this@CategoryFragment.adapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { dishes ->
                    adapter.updateData(dishes)
                }
            }
        }
    }
}