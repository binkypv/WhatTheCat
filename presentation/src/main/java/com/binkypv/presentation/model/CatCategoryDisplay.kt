package com.binkypv.presentation.model

import com.binkypv.domain.model.CatCategoryModel

data class CatCategoryDisplay(
    val id: Int,
    val name: String
)

fun CatCategoryModel.toDisplay() = CatCategoryDisplay(id, name.capitalize())