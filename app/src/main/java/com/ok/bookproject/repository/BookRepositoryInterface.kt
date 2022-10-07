package com.ok.bookproject.repository

import androidx.lifecycle.LiveData
import com.ok.bookproject.model.BookDataModel
import com.ok.bookproject.model.ImageResponse
import com.ok.bookproject.roomdb.BookDatabase
import com.ok.bookproject.util.Resource

interface BookRepositoryInterface {

    suspend fun insertBook(book: BookDataModel)

    suspend fun deleteBook(book: BookDataModel)

    fun getBook() :LiveData<List<BookDataModel>>

    suspend fun searchImg(imageStr: String) :  Resource<ImageResponse>
}