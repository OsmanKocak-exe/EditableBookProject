package com.ok.bookproject.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.common.truth.Truth.assertThat
import com.ok.bookproject.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.ok.bookproject.R
import com.ok.bookproject.getOrAwaitValue
import com.ok.bookproject.model.BookDataModel
import com.ok.bookproject.repo.FakeBookRepositoryTest
import com.ok.bookproject.roomdb.BookDatabase
import com.ok.bookproject.viewmodel.BookViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Named


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class BookDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    lateinit var fragmentFactory: BookFragmentFactory



    //private lateinit var viewModel: BookDetailsFragment

    @Before
    fun setup(){

        hiltRule.inject()
    }

    @Test
    fun navControlFromImageViewOnClicked(){
        val  navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<BookDetailsFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.bookImageView)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            BookDetailsFragmentDirections.actionBookDetailsFragmentToImageAPIFragment()
        )
    }

    @Test
    fun onBackPressedTest(){
        val  navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<BookDetailsFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()
    }

    @Test //Test Failed: Cannot access Hilted fragment '<BookDetailsFragment>'
    fun saveButtonTest(){
        val testViewModel = BookViewModel(FakeBookRepositoryTest())
        //issueIs -> this:Fragment need to be this:BookDetailsFragment
        launchFragmentInHiltContainer<BookDetailsFragment>(factory = fragmentFactory){


        }

        Espresso.onView(withId(R.id.editTextName)).perform(ViewActions.replaceText("NameTest"))
        Espresso.onView(withId(R.id.editTextDetails)).perform(ViewActions.replaceText("authorText"))
        Espresso.onView(withId(R.id.editTextDetails_extra)).perform(ViewActions.replaceText("genreText"))
        Espresso.onView(withId(R.id.editTextDetails_extra2)).perform(ViewActions.replaceText("1999"))

        Espresso.onView(withId(R.id.saveButton)).perform(ViewActions.click())

        /*
        assertThat(viewModel.bookList.getOrAwaitValue()).contains(
            BookDataModel("NameTest","authorText","genreText",1999,"")
        )
        */


    }

}