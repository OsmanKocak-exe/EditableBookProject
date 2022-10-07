package com.ok.bookproject.view

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.ok.bookproject.R
import com.ok.bookproject.adapter.ImageRecyclerAdapter
import com.ok.bookproject.databinding.FragmentImageApiBinding
import com.ok.bookproject.util.Status
import com.ok.bookproject.viewmodel.BookViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageAPIFragment @Inject constructor(
    val imageRecyclerAdapter: ImageRecyclerAdapter
) :Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: BookViewModel
    private var fragmentBinding : FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        var job : Job? = null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.searchImage(it.toString())
                    }
                }
            }
        }

        subscribeToObserves()
        binding.imageRecyView.adapter = imageRecyclerAdapter
        binding.imageRecyView.layoutManager = GridLayoutManager(requireContext(),3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }
    }

    private fun subscribeToObserves(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }
                    imageRecyclerAdapter.image = urls ?: listOf()
                    fragmentBinding?.imageApiProgressbar?.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error!",Toast.LENGTH_LONG).show()
                    fragmentBinding?.imageApiProgressbar?.visibility = View.GONE
                }
                Status.LOADING -> {
                    fragmentBinding?.imageApiProgressbar?.visibility = View.VISIBLE
                }
            }
        })

}
}