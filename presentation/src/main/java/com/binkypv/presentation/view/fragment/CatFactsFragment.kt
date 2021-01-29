package com.binkypv.presentation.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.binkypv.presentation.databinding.FragmentCatFactsBinding

class CatFactsFragment : BaseFragment<FragmentCatFactsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCatFactsBinding
        get() = FragmentCatFactsBinding::inflate

}