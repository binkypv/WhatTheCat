package com.binkypv.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.binkypv.presentation.adapter.CategoriesAdapter
import com.binkypv.presentation.databinding.FragmentCatCategoriesBinding
import com.binkypv.presentation.viewmodel.CatCategoriesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val LOADING_FLIPPER_CHILD = 0
private const val LIST_FLIPPER_CHILD = 1

class CatCategoriesFragment : BaseFragment<FragmentCatCategoriesBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCatCategoriesBinding
        get() = FragmentCatCategoriesBinding::inflate

    private val adapter: CategoriesAdapter by lazy {
        CategoriesAdapter { id, name ->
            navigateToPictures(id, name)
        }
    }
    private val catCategoriesViewModel: CatCategoriesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        catCategoriesViewModel.startCategories()
    }

    private fun initViews() {
        binding.catCategoryList.adapter = adapter
        binding.catCategoryList.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.categoriesFlipper.displayedChild = LOADING_FLIPPER_CHILD
    }

    private fun initObservers() {
        catCategoriesViewModel.categories.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            binding.categoriesFlipper.displayedChild = LIST_FLIPPER_CHILD
        })

        catCategoriesViewModel.loading.observe(viewLifecycleOwner, {
            binding.categoriesFlipper.displayedChild = LOADING_FLIPPER_CHILD
        })
    }

    private fun navigateToPictures(id: Int, name: String) {
        findNavController().navigate(
            CatCategoriesFragmentDirections.actionCatCategoriesFragmentToCatPicsFragment(id, name)
        )
    }
}