package com.binkypv.data.repository

import com.binkypv.domain.model.CatImageModel
import com.google.gson.annotations.SerializedName

data class CatImageEntity(
    @SerializedName("breeds") val breeds: List<String>,
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
) {
    fun toDomain() = CatImageModel(url)
}