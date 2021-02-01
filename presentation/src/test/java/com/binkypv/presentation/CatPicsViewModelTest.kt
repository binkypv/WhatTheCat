package com.binkypv.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.binkypv.domain.model.CatImageModel
import com.binkypv.domain.repository.CatFactsRepository
import com.binkypv.presentation.model.PicsListItem
import com.binkypv.presentation.model.PicsLoadingItem
import com.binkypv.presentation.model.toDisplay
import com.binkypv.presentation.utils.CoroutinesRule
import com.binkypv.presentation.viewmodel.CatPicsViewModel
import com.bumptech.glide.load.HttpException
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatPicsViewModelTest {
    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = CoroutinesRule()

    private val repo: CatFactsRepository = mockk()
    private val observer = mockk<Observer<List<PicsListItem>>>(relaxed = true)
    private val errorObserver = mockk<Observer<String>>(relaxed = true)
    private val loaderObserver = mockk<Observer<Unit>>(relaxed = true)
    private val viewmodel = CatPicsViewModel(repo)

    @Before
    fun setup() {
        MockKAnnotations.init()
        viewmodel.pics.observeForever(observer)
        viewmodel.error.observeForever(errorObserver)
        viewmodel.loading.observeForever(loaderObserver)
    }

    @Test
    fun when_entering_pics_screen_pics_loaded() {
        // given
        val catPicsModel = listOf(
            CatImageModel("url1"), CatImageModel("url2")
        )

        val result: List<PicsListItem> = mutableListOf<PicsListItem>().apply {
            addAll(catPicsModel.map { it.toDisplay() })
            add(PicsLoadingItem)
        }.toList()

        coEvery { repo.getPics(any(), any(), any()) } returns catPicsModel

        // when
        runBlocking {
            viewmodel.loadImages(1)
        }

        // then
        coVerify(exactly = 1) { repo.getPics(1, 25, 1) }
        verify { observer.onChanged(result) }
    }

    @Test
    fun given_network_error_when_fetching_pics_then_show_error() {
        // given
        coEvery { repo.getPics(any(), any(), any()) }.throws(HttpException("error"))

        // when
        runBlocking {
            viewmodel.loadImages(1)
        }

        // then
        coVerify(exactly = 1) { repo.getPics(1) }
        verify { errorObserver.onChanged("error") }
    }
}