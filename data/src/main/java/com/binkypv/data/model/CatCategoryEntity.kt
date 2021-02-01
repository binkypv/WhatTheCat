package com.binkypv.data.model

import com.binkypv.domain.model.CatCategoryModel
import com.google.gson.annotations.SerializedName

data class CatCategoryEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) {
    fun toDomain() = CatCategoryModel(id, name)
}