package com.binkypv.data.model

import com.binkypv.domain.model.CatFactModel
import com.google.gson.annotations.SerializedName

data class CatFactEntity(
    @SerializedName("status") val status: CatFactStatusEntity,
    @SerializedName("type") val type: String,
    @SerializedName("deleted") val deleted: Boolean,
    @SerializedName("__id") val id: String,
    @SerializedName("__v") val v: Int,
    @SerializedName("text") val text: String,
    @SerializedName("source") val source: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("used") val used: Boolean,
    @SerializedName("user") val user: String
) {
    fun toDomain() = CatFactModel(text)
}

data class CatFactStatusEntity(
    @SerializedName("verified") val verified: Boolean,
    @SerializedName("sentCount") val sentCount: Int
)
