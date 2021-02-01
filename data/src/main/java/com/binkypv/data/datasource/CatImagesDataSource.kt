package com.binkypv.data.datasource

import com.binkypv.data.model.CatCategoryEntity
import com.binkypv.data.model.CatImageEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface CatImagesDataSource {

    @GET("/v1/images/search")
    suspend fun getCatImage(
        @Query("category_ids") categoryIds: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null
    ): List<CatImageEntity>

    @GET("/v1/categories")
    suspend fun getCategories(): List<CatCategoryEntity>

}