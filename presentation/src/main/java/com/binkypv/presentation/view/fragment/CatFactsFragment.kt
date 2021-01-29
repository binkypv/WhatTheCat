package com.binkypv.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binkypv.presentation.databinding.FragmentCatFactsBinding
import com.binkypv.presentation.viewmodel.CatFactsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatFactsFragment : BaseFragment<FragmentCatFactsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCatFactsBinding
        get() = FragmentCatFactsBinding::inflate

    private val catFactsViewModel: CatFactsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        catFactsViewModel.onGetNewCatFact()
    }

    private fun initObservers() {
        catFactsViewModel.fact.observe(viewLifecycleOwner, {
            binding.catFactText.text = it.fact
        })
    }

}