package com.example.rickandmortyapi.ui.views

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentSingleViewBinding
import com.example.rickandmortyapi.ui.RAMViewModel

class SingleView : Fragment() {

    private lateinit var binding: FragmentSingleViewBinding
    private val viewModel by activityViewModels<RAMViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.randomCharacter.observe(viewLifecycleOwner) {
            binding.tvName.text = it.name
            binding.tvGender.text = it.gender
            binding.tvSpecies.text = it.species
            binding.ivCharacterImage.load(it.image) {
                error(R.drawable.ic_launcher_background)
            }
            if(viewModel.isFavorite(it.id)) {
                binding.ivLike.setBackgroundColor(Color.RED)
            } else {
                binding.ivLike.setBackgroundColor(Color.WHITE)
            }
        }

        binding.btRandom.setOnClickListener {
            viewModel.getRandomCharacter()
        }

        binding.btListView.setOnClickListener {
            findNavController().navigate(
                SingleViewDirections.actionSingleViewToListView()
            )
        }

        binding.ivLike.setOnClickListener {
            viewModel.randomCharacter.value?.let {
                if(viewModel.isFavorite(it.id)) {
                    binding.ivLike.setBackgroundColor(Color.WHITE)
                    viewModel.deleteCharacterFromDB(it)
                } else {
                    binding.ivLike.setBackgroundColor(Color.RED)
                    viewModel.saveCharacterToDB(it)
                }
            }
        }
    }
}