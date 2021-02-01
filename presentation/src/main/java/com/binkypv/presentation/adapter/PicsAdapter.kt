package com.binkypv.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binkypv.presentation.databinding.LoadingSquareItemBinding
import com.binkypv.presentation.databinding.RowCatPicBinding
import com.binkypv.presentation.model.CatImageDisplay
import com.binkypv.presentation.model.PicsListItem
import com.binkypv.presentation.model.PicsLoadingItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

private const val LOADING_ITEM = 0
private const val PIC_ITEM = 1

class PicsAdapter(private val onClick: (() -> Unit)) :
    ListAdapter<PicsListItem, PicsItemViewHolder>(picsDiffCallback) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PicsLoadingItem -> LOADING_ITEM
            else -> PIC_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            LOADING_ITEM -> {
                LoadingItemViewHolder(
                    LoadingSquareItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                PicViewHolder(
                    RowCatPicBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }

    override fun onBindViewHolder(holder: PicsItemViewHolder, position: Int) {
        if (holder is PicViewHolder) {
            holder.bind(getItem(position), onClick)
        }
    }
}

val picsDiffCallback = object : DiffUtil.ItemCallback<PicsListItem>() {
    override fun areItemsTheSame(
        oldItem: PicsListItem,
        newItem: PicsListItem,
    ) = oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(
        oldItem: PicsListItem,
        newItem: PicsListItem,
    ) = oldItem.areContentsTheSame(newItem)
}

class PicViewHolder(private val binding: RowCatPicBinding) :
    PicsItemViewHolder(binding.root) {
    fun bind(
        model: PicsListItem,
        onClick: (() -> Unit)
    ) {
        if (model is CatImageDisplay) {
            Glide.with(binding.catPic)
                .load(model.url)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.catPic)
            binding.root.setOnClickListener { onClick() }
        }
    }
}


class LoadingItemViewHolder(binding: LoadingSquareItemBinding) :
    PicsItemViewHolder(binding.root)

sealed class PicsItemViewHolder(view: View) : RecyclerView.ViewHolder(view)