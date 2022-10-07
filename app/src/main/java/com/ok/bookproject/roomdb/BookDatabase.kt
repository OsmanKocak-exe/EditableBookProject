package com.ok.bookproject.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ok.bookproject.model.BookDataModel

@Database(entities = [BookDataModel::class], version = 1)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao() : BookDAO
}