package com.binkypv.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.binkypv.domain.model.CatCategoryModel
import com.binkypv.domain.repository.CatFactsRepository
import com.binkypv.presentation.model.CatCategoryDisplay
import com.binkypv.presentation.model.toDisplay
import com.binkypv.presentation.utils.CoroutinesRule
import com.binkypv.presentation.viewmodel.CatCategoriesViewModel
import com.bumptech.glide.load.HttpException
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatCategoriesViewModelTest {
    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = CoroutinesRule()

    private val repo: CatFactsRepository = mockk()
    private val observer = mockk<Observer<List<CatCategoryDisplay>>>(relaxed = true)
    private val errorObserver = mockk<Observer<String>>(relaxed = true)
    private val loaderObserver = mockk<Observer<Unit>>(relaxed = true)
    private val viewmodel = CatCategoriesViewModel(repo)

    @Before
    fun setup() {
        MockKAnnotations.init()
        viewmodel.categories.observeForever(observer)
        viewmodel.error.observeForever(errorObserver)
        viewmodel.loading.observeForever(loaderObserver)
    }

    @Test
    fun when_entering_categories_screen_show_cat_categories() {
        // given
        val catCategoriesModel = listOf(CatCategoryModel(1, "a"), CatCategoryModel(2, "b"))

        coEvery { repo.getCategories() } returns catCategoriesModel

        // when
        runBlocking {
            viewmodel.startCategories()
        }

        // then
        coVerify(exactly = 1) { repo.getCategories() }
        verify { loaderObserver.onChanged(Unit) }
        verify { observer.onChanged(catCategoriesModel.map { it.toDisplay() }) }
    }

    @Test
    fun given_network_error_when_fetching_categories_then_show_error() {
        // given
        coEvery { repo.getCategories() }.throws(HttpException("error"))

        // when
        runBlocking {
            viewmodel.startCategories()
        }

        // then
        coVerify(exactly = 1) { repo.getCategories() }
        verify { loaderObserver.onChanged(Unit) }
        verify { errorObserver.onChanged("error") }
    }
}