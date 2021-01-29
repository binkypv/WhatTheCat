package com.binkypv.presentation.model

import com.binkypv.domain.model.CatImageModel

data class CatImageDisplay(
    val url: String
)

fun CatImageModel.toDisplay() = CatImageDisplay(url)