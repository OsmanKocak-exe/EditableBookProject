package com.ok.bookproject.view

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.ok.bookproject.R
import com.ok.bookproject.databinding.FragmentBookBinding
import com.ok.bookproject.databinding.FragmentDetailsBinding
import com.ok.bookproject.util.Status
import com.ok.bookproject.viewmodel.BookViewModel
import javax.inject.Inject

class BookDetailsFragment @Inject constructor(
    val glide: RequestManager
): Fragment(R.layout.fragment_details) {

    lateinit var viewModel: BookViewModel
    var test = String()
    private var fragmentBinding : FragmentDetailsBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        val binding = FragmentDetailsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObserves()

        binding.bookImageView.setOnClickListener {
            findNavController().navigate(BookDetailsFragmentDirections.actionBookDetailsFragmentToImageAPIFragment())
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener {
            viewModel.createBook(binding.editTextName.text.toString(),binding.editTextDetails.text.toString(),binding.editTextDetailsExtra.text.toString(),binding.editTextDetailsExtra2.text.toString())
        }
    }

    private fun subscribeToObserves(){
        viewModel.selectedImageURL.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.bookImageView)
            }
        })

        viewModel.insertBookMsg.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertedMassage()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }
}