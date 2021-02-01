package com.binkypv.data

import com.binkypv.data.model.CatCategoryEntity
import com.binkypv.data.model.CatFactEntity
import com.binkypv.data.model.CatFactStatusEntity
import com.binkypv.data.model.CatImageEntity
import com.binkypv.domain.model.CatCategoryModel
import com.binkypv.domain.model.CatFactModel
import com.binkypv.domain.model.CatImageModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class MapperTests {

    @Test
    fun map_cat_image_entity_to_cat_image_model() {
        // given
        val entity = CatImageEntity(
            listOf(),
            "id",
            "url",
            100,
            200
        )

        val expected = CatImageModel("url")

        // when
        val actual = entity.toDomain()

        // then
        assertEquals(actual, expected)
    }

    @Test
    fun map_cat_fact_entity_to_cat_fact_model() {
        // given
        val entity = CatFactEntity(
            CatFactStatusEntity(false, 1),
            "type",
            false,
            "id",
            0,
            "text",
            "source",
            "updatedAt",
            "createdAt",
            false,
            "user"
        )

        val expected = CatFactModel("text")

        // when
        val actual = entity.toDomain()

        // then
        assertEquals(actual, expected)
    }

    @Test
    fun map_cat_category_entity_to_cat_category_model() {
        // given
        val entity = CatCategoryEntity(
            1,
            "name"
        )

        val expected = CatCategoryModel(1, "name")

        // when
        val actual = entity.toDomain()

        // then
        assertEquals(actual, expected)
    }
}