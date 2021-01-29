package com.binkypv.domain.repository

import com.binkypv.domain.model.CatFactModel
import com.binkypv.domain.model.CatImageModel

interface CatFactsRepository {
    suspend fun getCatImage(): CatImageModel
    suspend fun getCatFact(): CatFactModel
}