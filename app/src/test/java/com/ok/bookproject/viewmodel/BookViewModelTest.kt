package com.ok.bookproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.ok.bookproject.MainCoroutineRule
import com.ok.bookproject.getOrAwaitValue
import com.ok.bookproject.repo.FakeBookRepository
import com.ok.bookproject.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class BookViewModelTest {

    @get:Rule
    var instantTestExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()



    private lateinit var viewModel: BookViewModel

    @Before
    fun setup(){
        viewModel = BookViewModel(FakeBookRepository())
    }

    @Test
    fun `insertBook() year = null OR empty #returns ERROR`(){
        viewModel.createBook("testName","testAuthor","testAuthor","")
        val value = viewModel.insertBookMsg.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insertBook() genre = null OR empty #returns ERROR`(){
        viewModel.createBook("testName","testAuthor","","1996")
        val value = viewModel.insertBookMsg.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insertBook() author = null OR empty #returns ERROR`(){
        viewModel.createBook("testName","","testAuthor","1996")
        val value = viewModel.insertBookMsg.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insertBook() name = null OR empty #returns ERROR`(){
        viewModel.createBook("","testAuthor","testAuthor","1996")
        val value = viewModel.insertBookMsg.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}