package com.binkypv.presentation.model

import com.binkypv.domain.model.CatImageModel

data class CatImageDisplay(
    val url: String
) : PicsListItem() {
    override fun areItemsTheSame(other: PicsListItem) =
        other is CatImageDisplay && other.url == url

    override fun areContentsTheSame(other: PicsListItem) =
        other is CatImageDisplay && other == this
}

fun CatImageModel.toDisplay() = CatImageDisplay(url)

sealed class PicsListItem {
    abstract fun areItemsTheSame(other: PicsListItem): Boolean
    abstract fun areContentsTheSame(other: PicsListItem): Boolean
}

object PicsLoadingItem : PicsListItem() {
    override fun areItemsTheSame(other: PicsListItem) =
        other is PicsLoadingItem

    override fun areContentsTheSame(other: PicsListItem) =
        other is PicsLoadingItem
}