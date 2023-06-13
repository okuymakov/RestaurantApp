package com.example.home.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.ui.extensions.launchOnStarted
import com.example.core.ui.viewbinding.viewBinding
import com.example.core.ui.views.RecyclerStateLayout
import com.example.domain.model.Category
import com.example.domain.network.Response
import com.example.home.R
import com.example.home.databinding.FragmentHomeBinding
import com.example.home.di.HomeComponent
import com.example.home.navigation.HomeNavigator
import com.example.home.ui.adapter.CategoryAdapter
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {
    @Inject
    internal lateinit var viewModelFactory: HomeViewModel.Factory
    @Inject
    internal lateinit var navigator: HomeNavigator
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }
    private val adapter: CategoryAdapter by lazy { CategoryAdapter(::onClick) }
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private fun onClick(category: Category) {
        navigator.navigateToCategory(category.name)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        HomeComponent.get().inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listState.updateState(RecyclerStateLayout.State.Loading)
        binding.listState.setOnRetryClickListener {
            binding.listState.updateState(RecyclerStateLayout.State.Loading)
            viewModel.fetchData()
        }
        binding.categories.apply {
            adapter = this@HomeFragment.adapter
            setHasFixedSize(true)
        }
        launchOnStarted {
            viewModel.state.collect { res ->
                when(res) {
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