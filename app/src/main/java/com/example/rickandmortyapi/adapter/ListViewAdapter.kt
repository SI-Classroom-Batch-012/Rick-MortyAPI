package com.example.rickandmortyapi.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.data.model.RemoteModel
import com.example.rickandmortyapi.databinding.ListItemBinding
import com.example.rickandmortyapi.ui.RAMViewModel

class ListViewAdapter(
    private var dataset: List<RemoteModel>,
    private var viewModel: RAMViewModel
): RecyclerView.Adapter<ListViewAdapter.RAMViewHolder>() {

    // Adapter für ListView
    inner class RAMViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RAMViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RAMViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: List<RemoteModel>) {
        dataset = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RAMViewHolder, position: Int) {
        val character = dataset[position]

        holder.binding.tvName.text = character.name
        holder.binding.tvGender.text = character.gender
        holder.binding.tvSpecies.text = character.species

        holder.binding.ivCharacterImage.load(character.image) {
            error(R.drawable.ic_launcher_background)
        }

        if(viewModel.isFavorite(character.id)) {
            holder.binding.ivLike.setBackgroundColor(Color.RED)
        } else {
            holder.binding.ivLike.setBackgroundColor(Color.WHITE)
        }

        holder.binding.ivLike.setOnClickListener {
            character.let {
                if(viewModel.isFavorite(it.id)) {
                    holder.binding.ivLike.setBackgroundColor(Color.WHITE)
                    viewModel.deleteCharacterFromDB(it)
                } else {
                    holder.binding.ivLike.setBackgroundColor(Color.RED)
                    viewModel.saveCharacterToDB(it)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}