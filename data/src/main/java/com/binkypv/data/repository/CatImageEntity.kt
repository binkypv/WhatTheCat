package com.binkypv.data.repository

import com.binkypv.domain.model.CatImageModel
import com.google.gson.annotations.SerializedName

data class CatImageEntity(
    @SerializedName("breeds") val breeds: List<BreedEntity>,
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
) {
    fun toDomain() = CatImageModel(url)
}

data class BreedEntity(
    @SerializedName("weight") val weight: WeightEntity,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("vcahospitals_url") val vcahospitalsUrl: String?,
    @SerializedName("temperament") val temperament: String?,
    @SerializedName("origin") val origin: String?,
    @SerializedName("country_codes") val country_codes: String?,
    @SerializedName("country_code") val country_code: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("life_span") val lifeSpan: String?,
    @SerializedName("indoor") val indoor: Int,
    @SerializedName("alt_names") val altNames: String?,
    @SerializedName("adaptability") val adaptability: Int?,
    @SerializedName("affection_level") val affectionLevel: Int?,
    @SerializedName("child_friendly") val childFriendly: Int?,
    @SerializedName("dog_friendly") val dogFriendly: Int?,
    @SerializedName("energy_level") val energyLevel: Int?,
    @SerializedName("grooming") val groooming: Int?,
    @SerializedName("health_issues") val healthIssues: Int?,
    @SerializedName("intelligence") val intelligence: Int?,
    @SerializedName("shedding_level") val sheddingLevel: Int?,
    @SerializedName("social_needs") val socialNeeds: Int?,
    @SerializedName("stranger_friendly") val strangerFriendly: Int?,
    @SerializedName("vocalisation") val vocalisation: Int?,
    @SerializedName("experimental") val experimental: Int?,
    @SerializedName("hairless") val hairless: Int?,
    @SerializedName("natural") val natural: Int?,
    @SerializedName("rare") val rare: Int?,
    @SerializedName("rex") val rex: Int?,
    @SerializedName("suppressed_tail") val suppressedTail: Int?,
    @SerializedName("short_legs") val shortLegs: Int?,
    @SerializedName("wikipedia_url") val wikipediaUrl: String?,
    @SerializedName("hypoallergenic") val hypoallergenic: Int?,
    @SerializedName("reference_image_id") val referenceImageId: String?
)

data class WeightEntity(
    @SerializedName("imperial") val imperial: String,
    @SerializedName("metric") val metric: String
)