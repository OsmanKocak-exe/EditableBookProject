package com.ok.bookproject.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ok.bookproject.R
import com.ok.bookproject.api.RetrofitAPI
import com.ok.bookproject.repository.BookRepository
import com.ok.bookproject.repository.BookRepositoryInterface
import com.ok.bookproject.roomdb.BookDAO
import com.ok.bookproject.roomdb.BookDatabase
import com.ok.bookproject.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,BookDatabase::class.java,"BookDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database: BookDatabase) = database.bookDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI(): RetrofitAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun  injectNormRepo(dao: BookDAO, api: RetrofitAPI) = BookRepository(dao,api) as BookRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background))
}