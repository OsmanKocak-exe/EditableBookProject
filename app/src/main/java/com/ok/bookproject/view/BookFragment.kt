package com.ok.bookproject.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ok.bookproject.R
import com.ok.bookproject.adapter.BookRecyAdapter
import com.ok.bookproject.databinding.FragmentBookBinding
import com.ok.bookproject.viewmodel.BookViewModel
import javax.inject.Inject

class BookFragment @Inject constructor(
    val bookRecyAdapter: BookRecyAdapter
) : Fragment(R.layout.fragment_book) {

    private var fragmentBinding : FragmentBookBinding? = null
    lateinit var viewModel: BookViewModel
    private var swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPos = viewHolder.layoutPosition
            val selectedBook = bookRecyAdapter.books[layoutPos]
            viewModel.deleteBook(selectedBook)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        val binding = FragmentBookBinding.bind(view)
        fragmentBinding = binding

        subscribeOnObservers()

        binding.recyclerViewBook.adapter = bookRecyAdapter
        binding.recyclerViewBook.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewBook)

        binding.fAButton.setOnClickListener {
            findNavController().navigate(BookFragmentDirections.actionBookFragmentToBookDetailsFragment())
        }
    }

    private fun subscribeOnObservers(){
        viewModel.bookList.observe(viewLifecycleOwner, Observer {
            bookRecyAdapter.books = it
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}