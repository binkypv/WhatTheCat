package com.binkypv.data.datasource

import com.binkypv.data.repository.CatImageEntity
import retrofit2.http.GET

interface CatImagesDataSource {

    @GET("/v1/images/search")
    suspend fun getCatImage(): List<CatImageEntity>

}