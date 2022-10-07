package com.ok.bookproject.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ok.bookproject.model.BookDataModel
import com.ok.bookproject.model.ImageResponse
import com.ok.bookproject.repository.BookRepositoryInterface
import com.ok.bookproject.util.Resource

class FakeBookRepository : BookRepositoryInterface {

    private val books = mutableListOf<BookDataModel>()
    private val liveData = MutableLiveData<List<BookDataModel>>(books)


    override suspend fun insertBook(book: BookDataModel) {
        books.add(book)
        refreshData()
    }

    override suspend fun deleteBook(book: BookDataModel) {
        books.remove(book)
        refreshData()
    }

    override fun getBook(): LiveData<List<BookDataModel>> {
        return liveData
    }

    override suspend fun searchImg(imageStr: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        liveData.postValue(books)
    }
}