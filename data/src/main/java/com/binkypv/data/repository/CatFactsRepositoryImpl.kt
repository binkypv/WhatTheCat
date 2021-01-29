package com.binkypv.data.repository

import com.binkypv.data.datasource.CatFactsDataSource
import com.binkypv.data.datasource.CatImagesDataSource
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

}