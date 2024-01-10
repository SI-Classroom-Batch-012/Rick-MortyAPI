package com.example.rickandmortyapi.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.rickandmortyapi.adapter.ListViewAdapter
import com.example.rickandmortyapi.databinding.FragmentListViewBinding
import com.example.rickandmortyapi.ui.RAMViewModel

class ListView : Fragment() {

    private lateinit var binding: FragmentListViewBinding
    private val viewModel by activityViewModels<RAMViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ListViewAdapter(listOf(), viewModel)
        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.rvListItems)
        binding.rvListItems.adapter = adapter

        viewModel.characterList.observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        binding.btPrevPage.setOnClickListener {
            viewModel.getPrevPage()
        }

        binding.btNextPage.setOnClickListener {
            viewModel.getNextPage()
        }

        binding.btBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}