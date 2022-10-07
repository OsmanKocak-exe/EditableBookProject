package com.ok.bookproject.repository

import androidx.lifecycle.LiveData
import com.ok.bookproject.api.RetrofitAPI
import com.ok.bookproject.model.BookDataModel
import com.ok.bookproject.model.ImageResponse
import com.ok.bookproject.roomdb.BookDAO
import com.ok.bookproject.roomdb.BookDatabase
import com.ok.bookproject.util.Resource
import com.ok.bookproject.view.ImageAPIFragment
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookDao: BookDAO, private val retrofitApi: RetrofitAPI) : BookRepositoryInterface {
    override suspend fun insertBook(book: BookDataModel) {
        bookDao.insertBook(book)
    }

    override suspend fun deleteBook(book: BookDataModel) {
        bookDao.deleteBook(book)
    }

    override fun getBook(): LiveData<List<BookDataModel>> {
        return bookDao.observeBooks()
    }

    override suspend fun searchImg(imageStr: String): Resource<ImageResponse> {
        return try {
            val response = retrofitApi.imageSearch(imageStr)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("Error", null)
            } else{
                Resource.error("Error", null)
            }
        } catch (e: Exception){
            Resource.error("No Data!",null)
        }
    }
}