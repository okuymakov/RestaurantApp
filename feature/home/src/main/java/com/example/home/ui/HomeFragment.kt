package com.example.home.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Category
import com.example.home.R
import com.example.home.di.HomeComponent
import com.example.home.navigation.HomeNavigator
import com.example.home.ui.adapter.CategoryAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {
    @Inject
    internal lateinit var viewModelFactory: HomeViewModel.Factory
    @Inject
    internal lateinit var navigator: HomeNavigator
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }
    private val adapter: CategoryAdapter by lazy { CategoryAdapter(::onClick) }

    private fun onClick(category: Category) {
        navigator.navigateToCategory(category.id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        HomeComponent.get().inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.categories).apply {
            adapter = this@HomeFragment.adapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { categories ->
                    adapter.updateData(categories)
                }
            }
        }
    }
}