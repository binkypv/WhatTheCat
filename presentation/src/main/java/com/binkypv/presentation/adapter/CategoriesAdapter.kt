package com.binkypv.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binkypv.presentation.databinding.RowCatCategoryBinding
import com.binkypv.presentation.model.CatCategoryDisplay

class CategoriesAdapter(private val onClick: ((Int, String) -> Unit)) :
    ListAdapter<CatCategoryDisplay, CatCategoryViewHolder>(categoriesDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CatCategoryViewHolder(
            RowCatCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CatCategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}

val categoriesDiffCallback = object : DiffUtil.ItemCallback<CatCategoryDisplay>() {
    override fun areItemsTheSame(
        oldItem: CatCategoryDisplay,
        newItem: CatCategoryDisplay,
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CatCategoryDisplay,
        newItem: CatCategoryDisplay,
    ) = oldItem == newItem
}

class CatCategoryViewHolder(private val binding: RowCatCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        model: CatCategoryDisplay,
        onClick: (Int, String) -> Unit
    ) {
        binding.catCategory.text = model.name
        binding.root.setOnClickListener { onClick(model.id, model.name) }
    }
}