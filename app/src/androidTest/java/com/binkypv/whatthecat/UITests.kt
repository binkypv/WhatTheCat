package com.binkypv.whatthecat

import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.binkypv.presentation.adapter.CatCategoryViewHolder
import com.binkypv.presentation.model.CatCategoryDisplay
import com.binkypv.presentation.model.CatFactDisplay
import com.binkypv.presentation.model.CatImageDisplay
import com.binkypv.presentation.model.PicsListItem
import com.binkypv.presentation.view.activity.CatFactsActivity
import com.binkypv.presentation.viewmodel.CatCategoriesViewModel
import com.binkypv.presentation.viewmodel.CatFactsViewModel
import com.binkypv.presentation.viewmodel.CatPicsViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class UITests {
    private lateinit var scenario: ActivityScenario<CatFactsActivity>

    private lateinit var catFactsViewModel: CatFactsViewModel
    private lateinit var catCategoriesViewModel: CatCategoriesViewModel
    private lateinit var catPicsViewModel: CatPicsViewModel

    private lateinit var fact: MutableLiveData<CatFactDisplay>
    private lateinit var image: MutableLiveData<CatImageDisplay>
    private lateinit var categories: MutableLiveData<List<CatCategoryDisplay>>
    private lateinit var pics: MutableLiveData<List<PicsListItem>>

    private lateinit var module: Module

    @Before
    fun before() {
        MockKAnnotations.init(this)
        catFactsViewModel = mockk(relaxed = true)
        catCategoriesViewModel = mockk(relaxed = true)
        catPicsViewModel = mockk(relaxed = true)

        fact = MutableLiveData()
        image = MutableLiveData()
        categories = MutableLiveData()
        pics = MutableLiveData()

        every { catFactsViewModel.fact } returns fact
        every { catFactsViewModel.img } returns image
        every { catCategoriesViewModel.categories } returns categories
        every { catPicsViewModel.pics } returns pics

        module = module(createdAtStart = true, override = true) {
            single { catFactsViewModel }
            single { catCategoriesViewModel }
            single { catPicsViewModel }
        }

        loadKoinModules(module)
        scenario = launchActivity()
    }

    @After
    fun after() {
        scenario.close()
        unloadKoinModules(module)
    }

    @Test
    fun show_cat_fact_and_image_when_opening() {
        fact.postValue(CatFactDisplay("fact"))
        onView(withId(R.id.cat_fact_text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun when_navigating_to_categories_screen_show_categories_list() {
        categories.postValue(listOf(CatCategoryDisplay(1,"category")))
        onView(withId(R.id.categories_button)).perform(click())
        onView(withId(R.id.cat_category_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.categories_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun when_clicking_on_category_display_images() {
        categories.postValue(listOf(CatCategoryDisplay(1,"category")))
        onView(withId(R.id.categories_button)).perform(click())
        onView(withId(R.id.cat_category_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CatCategoryViewHolder>(
            0,
            click()))
        onView(withId(R.id.pics_root))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}