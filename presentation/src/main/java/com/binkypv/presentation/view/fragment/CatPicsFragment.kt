package com.binkypv.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.binkypv.presentation.R
import com.binkypv.presentation.adapter.PicsAdapter
import com.binkypv.presentation.databinding.FragmentCatPicsBinding
import com.binkypv.presentation.utils.GridSpacingItemDecoration
import com.binkypv.presentation.utils.configurePaging
import com.binkypv.presentation.viewmodel.CatPicsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatPicsFragment : BaseFragment<FragmentCatPicsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCatPicsBinding
        get() = FragmentCatPicsBinding::inflate

    private val adapter: PicsAdapter by lazy {
        PicsAdapter { showPicture() }
    }
    private val catPicsViewModel: CatPicsViewModel by viewModel()
    private val args: CatPicsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        catPicsViewModel.startPics(args.categoryId)
    }

    fun initViews() {
        binding.catPicList.layoutManager = GridLayoutManager(context, 2)
        binding.catPicList.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                resources.getDimensionPixelSize(R.dimen.small_margin),
                true
            )
        )
        binding.catPicList.adapter = adapter
        binding.catPicList.configurePaging(true) {
            catPicsViewModel.loadImages(args.categoryId)
        }
        binding.categoryTitle.text = args.categoryName
    }

    fun initObservers() {
        catPicsViewModel.pics.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    fun showPicture() {
        // do nothing
    }

}