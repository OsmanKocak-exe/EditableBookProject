package com.ok.bookproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ok.bookproject.model.BookDataModel
import com.ok.bookproject.model.ImageResponse
import com.ok.bookproject.repository.BookRepositoryInterface
import com.ok.bookproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookRepositoryInterface
) : ViewModel() {
  //ENCAPSULATE()
    //GetBooks -> BookAPI
    val bookList = repository.getBook()

    //GetImages -> ImageAPIFragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    //GetSelectedImage -> ImageAPIFragment
    private val selectedImage = MutableLiveData<String>()
    val selectedImageURL: LiveData<String>
        get() = selectedImage

    //statusHolder -> BookDetailsFragment
    private var insertStatusHolderMassage = MutableLiveData<Resource<BookDataModel>>()
    val insertBookMsg : LiveData<Resource<BookDataModel>>
        get() = insertStatusHolderMassage

    fun resetInsertedMassage(){
        insertStatusHolderMassage = MutableLiveData<Resource<BookDataModel>>()
    }

    fun setSelectedImage(url: String){
        selectedImage.postValue(url)
    }

    fun deleteBook(book: BookDataModel) = viewModelScope.launch{
        repository.deleteBook(book)
    }

    fun insertBook(book: BookDataModel) = viewModelScope.launch {
        repository.insertBook(book)
    }

    fun createBook(name: String,
                   author: String,
                   genre: String,
                   year: String, ){
        if (name.isEmpty() || author.isEmpty() || genre.isEmpty() || year.isEmpty()){
            insertStatusHolderMassage.postValue(Resource.error("Text areas cannot be empty",null))
            return
        }
        val yearInt = try {
            year.toInt()
        }catch (e: Exception){
            insertStatusHolderMassage.postValue(Resource.error("Year should be number",null))
            return
        }

        val book = BookDataModel(name,author,genre,yearInt,selectedImage.value ?: "empty_url")
        insertBook(book)
        setSelectedImage("")
        insertStatusHolderMassage.postValue(Resource.success(book))
    }

    fun searchImage(string: String){
        if (string.isEmpty()){
            return
        }

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImg(string)
            images.value = response
        }
    }

}