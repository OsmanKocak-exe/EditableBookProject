package com.ok.bookproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Books_Table")
data class BookDataModel(
    var name: String,
    var author: String,
    var genre: String,
    var year: Int,
    var imgUrl: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)