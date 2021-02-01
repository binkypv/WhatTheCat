package com.binkypv.data.repository

import com.binkypv.data.datasource.CatFactsDataSource
import com.binkypv.data.datasource.CatImagesDataSource
import com.binkypv.domain.model.CatCategoryModel
import com.binkypv.domain.model.CatFactModel
import com.binkypv.domain.model.CatImageModel
import com.binkypv.domain.repository.CatFactsRepository

class CatFactsRepositoryImpl(
    private val catImagesDataSource: CatImagesDataSource,
    private val catFactsDataSource: CatFactsDataSource
) : CatFactsRepository {
    override suspend fun getCatImage(): CatImageModel =
        catImagesDataSource.getCatImage().first().toDomain()

    override suspend fun getCatFact(): CatFactModel =
        catFactsDataSource.getCatFact().toDomain()

    override suspend fun getCategories(): List<CatCategoryModel> =
        catImagesDataSource.getCategories().map { it.toDomain() }

    override suspend fun getPics(categoryId: Int, limit: Int?, page: Int?): List<CatImageModel> =
        catImagesDataSource.getCatImage(categoryId, limit, page).map { it.toDomain() }
}