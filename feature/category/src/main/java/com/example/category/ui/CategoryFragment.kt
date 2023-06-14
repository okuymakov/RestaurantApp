package com.example.category.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.category.R
import com.example.category.databinding.FragmentCategoryBinding
import com.example.category.di.CategoryComponent
import com.example.category.navigation.CategoryNavigator
import com.example.category.ui.adapter.DishAdapter
import com.example.category.ui.adapter.TagAdapter
import com.example.core.ui.extensions.launchOnStarted
import com.example.core.ui.recyclerview.itemdecorations.GridOffsetDecoration
import com.example.core.ui.viewbinding.viewBinding
import com.example.core.ui.views.RecyclerStateLayout
import com.example.domain.model.Dish
import javax.inject.Inject

class CategoryFragment : Fragment(R.layout.fragment_category) {
    @Inject
    internal lateinit var viewModelFactory: CategoryViewModel.Factory

    @Inject
    internal lateinit var navigator: CategoryNavigator
    private val viewModel: CategoryViewModel by viewModels { viewModelFactory }
    private val dishAdapter: DishAdapter by lazy { DishAdapter(::onClick) }
    private val tagAdapter: TagAdapter by lazy { TagAdapter(::onTagClick) }
    private val binding by viewBinding(FragmentCategoryBinding::bind)

    private fun onClick(dish: Dish) {
        navigator.navigateToProduct(dish)
    }

    private fun onTagClick(tag: SelectableTag) {
        viewModel.selectTag(tag)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        CategoryComponent.get().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchDishes()
        binding.listState.setOnRetryClickListener {
            viewModel.fetchDishes()
        }
        binding.tags.apply {
            adapter = this@CategoryFragment.tagAdapter
            setHasFixedSize(true)
        }
        binding.dishes.apply {
            adapter = this@CategoryFragment.dishAdapter
            addItemDecoration(
                GridOffsetDecoration(
                    resources.getDimension(com.example.core.ui.R.dimen.small_100).toInt(),
                    resources.getDimension(com.example.core.ui.R.dimen.small_175).toInt()
                )
            )
        }
        launchOnStarted {
            viewModel.state.collect { state ->
                tagAdapter.items = state.tags
                when {
                    state.isLoading -> {
                        binding.listState.updateState(RecyclerStateLayout.State.Loading)
                    }
                    state.hasApiError -> {
                        binding.listState.updateState(RecyclerStateLayout.State.Error)
                        binding.listState.errorText =
                            resources.getString(com.example.core.ui.R.string.api_error)
                    }
                    state.hasNoInternet -> {
                        binding.listState.updateState(RecyclerStateLayout.State.Error)
                        binding.listState.errorText =
                            resources.getString(com.example.core.ui.R.string.no_internet_error)
                    }
                    state.hasUnknownError -> {
                        binding.listState.updateState(RecyclerStateLayout.State.Error)
                        binding.listState.errorText =
                            resources.getString(com.example.core.ui.R.string.unknown_error)
                    }
                    else -> {
                        if (state.dishes.isNotEmpty()) {
                            binding.listState.updateState(RecyclerStateLayout.State.Success)
                            dishAdapter.updateData(state.dishes)
                        } else {
                            binding.listState.updateState(RecyclerStateLayout.State.Empty)
                        }
                    }
                }
            }
        }
    }
}