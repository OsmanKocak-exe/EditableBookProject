package com.ok.bookproject.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.ok.bookproject.roomdb.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("testDataBase")
    fun injectInMemoryRoom(@ApplicationContext context: Context) = Room.inMemoryDatabaseBuilder(context, BookDatabase::class.java)
        .allowMainThreadQueries().build()
}