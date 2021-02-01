package com.binkypv.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.binkypv.domain.model.CatFactModel
import com.binkypv.domain.model.CatImageModel
import com.binkypv.domain.repository.CatFactsRepository
import com.binkypv.presentation.model.CatFactDisplay
import com.binkypv.presentation.model.CatImageDisplay
import com.binkypv.presentation.model.toDisplay
import com.binkypv.presentation.utils.CoroutinesRule
import com.binkypv.presentation.viewmodel.CatFactsViewModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatFactsViewModelTest {
    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = CoroutinesRule()

    private val repo: CatFactsRepository = mockk()
    private val imageObserver = mockk<Observer<CatImageDisplay>>(relaxed = true)
    private val factObserver = mockk<Observer<CatFactDisplay>>(relaxed = true)
    private val errorObserver = mockk<Observer<String>>(relaxed = true)
    private val loaderObserver = mockk<Observer<Unit>>(relaxed = true)
    private val viewmodel = CatFactsViewModel(repo)

    @Before
    fun setup() {
        MockKAnnotations.init()
        viewmodel.img.observeForever(imageObserver)
        viewmodel.fact.observeForever(factObserver)
        viewmodel.error.observeForever(errorObserver)
        viewmodel.loading.observeForever(loaderObserver)
    }

    @Test
    fun given_when_new_cat_fact_queried_show_result() {
        // given
        val catFactModel = CatFactModel("text")
        val catImageModel = CatImageModel("url")

        coEvery { repo.getCatImage() } returns catImageModel
        coEvery { repo.getCatFact() } returns catFactModel

        // when
        runBlocking {
            viewmodel.onGetNewCatFact()
        }

        // then
        coVerify(exactly = 1) { repo.getCatImage() }
        coVerify(exactly = 1) { repo.getCatFact() }
        verify { loaderObserver.onChanged(Unit) }
        verify { factObserver.onChanged(catFactModel.toDisplay()) }
        verify { imageObserver.onChanged(catImageModel.toDisplay()) }
    }
}