package com.binkypv.domain.repository

import com.binkypv.domain.model.CatCategoryModel
import com.binkypv.domain.model.CatFactModel
import com.binkypv.domain.model.CatImageModel

interface CatFactsRepository {
    suspend fun getCatImage(): CatImageModel
    suspend fun getCatFact(): CatFactModel
    suspend fun getCategories(): List<CatCategoryModel>
    suspend fun getPics(categoryId: Int, limit: Int? = 25, page: Int? = 1): List<CatImageModel>
}