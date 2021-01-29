package com.binkypv.data.datasource

import com.binkypv.data.model.CatFactEntity
import retrofit2.http.GET

interface CatFactsDataSource {

    @GET("/facts/random")
    suspend fun getCatFact(): CatFactEntity

}