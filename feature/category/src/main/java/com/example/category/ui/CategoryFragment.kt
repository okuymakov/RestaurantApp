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
import com.example.core.ui.extensions.launchOnStarted
import com.example.core.ui.viewbinding.viewBinding
import com.example.core.ui.views.RecyclerStateLayout
import com.example.domain.model.Dish
import com.example.domain.network.Response
import javax.inject.Inject

class CategoryFragment : Fragment(R.layout.fragment_category) {
    @Inject
    internal lateinit var viewModelFactory: CategoryViewModel.Factory

    @Inject
    internal lateinit var navigator: CategoryNavigator
    private val viewModel: CategoryViewModel by viewModels { viewModelFactory }
    private val adapter: DishAdapter by lazy { DishAdapter(::onClick) }
    private val binding by viewBinding(FragmentCategoryBinding::bind)

    private fun onClick(dish: Dish) {
        navigator.navigateToProduct(dish)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        CategoryComponent.get().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listState.updateState(RecyclerStateLayout.State.Loading)
        binding.listState.setOnRetryClickListener {
            binding.listState.updateState(RecyclerStateLayout.State.Loading)
            viewModel.fetchData()
        }
        binding.dishes.apply {
            adapter = this@CategoryFragment.adapter
            setHasFixedSize(true)
        }
        launchOnStarted {
            viewModel.state.collect { res ->
                when (res) {
                    is Response.Failure.ApiError -> {
                        binding.listState.updateState(RecyclerStateLayout.State.Error)
                        binding.listState.errorText =
                            resources.getString(com.example.core.ui.R.string.api_error)
                    }
                    is Response.Failure.NoInternetError -> {
                        binding.listState.updateState(RecyclerStateLayout.State.Error)
                        binding.listState.errorText =
                            resources.getString(com.example.core.ui.R.string.no_internet_error)
                    }
                    is Response.Failure.UnknownError -> {
                        binding.listState.updateState(RecyclerStateLayout.State.Error)
                        binding.listState.errorText =
                            resources.getString(com.example.core.ui.R.string.unknown_error)
                    }
                    is Response.Success -> {
                        if (res.data.isNotEmpty()) {
                            binding.listState.updateState(RecyclerStateLayout.State.Success)
                            adapter.updateData(res.data)
                        }
                    }
                }
            }
        }
    }
}