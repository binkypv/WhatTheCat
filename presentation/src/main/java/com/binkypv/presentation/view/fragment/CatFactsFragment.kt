package com.binkypv.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.binkypv.presentation.R
import com.binkypv.presentation.databinding.FragmentCatFactsBinding
import com.binkypv.presentation.viewmodel.CatFactsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val LOADING_FLIPPER_VIEW = 0
private const val IMAGE_FLIPPER_VIEW = 1

class CatFactsFragment : BaseFragment<FragmentCatFactsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCatFactsBinding
        get() = FragmentCatFactsBinding::inflate

    private val catFactsViewModel: CatFactsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
        catFactsViewModel.onGetNewCatFact()
    }

    private fun initListeners() {
        binding.catButton.setOnClickListener {
            catFactsViewModel.onGetNewCatFact()
        }
    }

    private fun initObservers() {
        catFactsViewModel.loading.observe(viewLifecycleOwner, {
            binding.catHeader.displayedChild = LOADING_FLIPPER_VIEW
            binding.catFactText.visibility = View.GONE
        })

        catFactsViewModel.fact.observe(viewLifecycleOwner, {
            binding.catFactText.text = it.fact
            binding.catFactText.visibility = View.VISIBLE
        })

        catFactsViewModel.img.observe(viewLifecycleOwner, {
            Glide.with(binding.catImage)
                .load(it.url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.catImage)
            binding.catHeader.displayedChild = IMAGE_FLIPPER_VIEW
        })
    }

}