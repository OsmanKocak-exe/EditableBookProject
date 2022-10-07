package com.ok.bookproject.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.ok.bookproject.adapter.BookRecyAdapter
import com.ok.bookproject.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class BookFragmentFactory @Inject constructor(
    private val glide: RequestManager,
    private val bookRecyAdapter: BookRecyAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            ImageAPIFragment::class.java.name -> ImageAPIFragment(imageRecyclerAdapter)
            BookFragment::class.java.name -> BookFragment(bookRecyAdapter)
            BookDetailsFragment::class.java.name -> BookDetailsFragment(glide)
            else -> return super.instantiate(classLoader, className)
        }


    }

}