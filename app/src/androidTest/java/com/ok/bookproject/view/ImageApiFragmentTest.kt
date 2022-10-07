package com.ok.bookproject.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.ok.bookproject.R
import com.ok.bookproject.adapter.ImageRecyclerAdapter
import com.ok.bookproject.databinding.FragmentBookBinding
import com.ok.bookproject.launchFragmentInHiltContainer
import com.ok.bookproject.repo.FakeBookRepositoryTest
import com.ok.bookproject.viewmodel.BookViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi

class ImageApiFragmentTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: BookFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test //Test Failed: Cannot access Hilted fragment '<ImageAPIFragment>'
    fun selectImage(){
        val navController = Mockito.mock(NavController::class.java)
        val testViewModel = BookViewModel(FakeBookRepositoryTest())
        val fakeSelectedURL = "@TestIMG_URL"

        //issueIs -> this:Fragment need to be this:ImageAPIFragment
        launchFragmentInHiltContainer<ImageAPIFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)

        }

        Espresso.onView(ViewMatchers.withId(R.id.imageRecyView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(0,ViewActions.click())
        )
    }


}