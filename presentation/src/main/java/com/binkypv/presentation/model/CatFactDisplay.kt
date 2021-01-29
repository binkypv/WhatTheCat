package com.binkypv.presentation.model

import com.binkypv.domain.model.CatFactModel

data class CatFactDisplay(
    val fact: String
)

fun CatFactModel.toDisplay() = CatFactDisplay(fact)