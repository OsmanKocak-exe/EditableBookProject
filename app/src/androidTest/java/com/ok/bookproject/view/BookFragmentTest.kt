package com.ok.bookproject.view

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.ok.bookproject.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import com.ok.bookproject.R
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
class BookFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Inject
    lateinit var fragmentFactory: BookFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }



    @Test
    fun testBookDetailsFragmentNavigationFAB(){

        val navController = Mockito.mock(NavController::class.java)
            launchFragmentInHiltContainer<BookFragment>(factory = fragmentFactory) {
                Navigation.setViewNavController(requireView(),navController)
            }

        Espresso.onView(ViewMatchers.withId(R.id.fAButton)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            BookFragmentDirections.actionBookFragmentToBookDetailsFragment()
        )
    }
}