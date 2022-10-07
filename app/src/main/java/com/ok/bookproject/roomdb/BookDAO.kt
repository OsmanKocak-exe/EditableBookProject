package com.ok.bookproject.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ok.bookproject.model.BookDataModel

@Dao
interface BookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookDataModel)
    @Delete
    suspend fun deleteBook(book: BookDataModel)
    @Query("SELECT * FROM Books_Table")
    fun observeBooks(): LiveData<List<BookDataModel>>
}