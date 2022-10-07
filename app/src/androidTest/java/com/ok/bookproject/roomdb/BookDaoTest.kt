package com.ok.bookproject.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.ok.bookproject.getOrAwaitValue
import com.ok.bookproject.model.BookDataModel
import com.ok.bookproject.util.Status
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class BookDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDataBase")
    lateinit var database: BookDatabase

    private lateinit var dao : BookDAO


    @Before
    fun setup(){
        hiltRule.inject()
        dao = database.bookDao()
    }

    @Test
    fun insertBookTesting() = runBlocking {
        val exampleBook = BookDataModel("nameTest","authorTest","genreTest",1414,"urlTest",0)
        dao.insertBook(exampleBook)
        val list = dao.observeBooks().getOrAwaitValue()
        assertThat(list).contains(exampleBook)
    }

    @Test
    fun delateBookTesting() = runBlocking {
        val exampleBook = BookDataModel("nameTest","authorTest","genreTest",1414,"urlTest",1)
        dao.insertBook(exampleBook)
        dao.deleteBook(exampleBook)
        val list = dao.observeBooks().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleBook)
    }


    @After
    fun shutDown(){
        database.close()
    }



}