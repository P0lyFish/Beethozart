package com.example.beethozart.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beethozart.R
import com.example.beethozart.databinding.ListItemArtistBinding
import com.example.beethozart.entities.Artist

class ArtistAdapter: ListAdapter<Artist, ArtistAdapter.ViewModel>(ArtistDiffCallback()) {

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        return ViewModel.from(parent)
    }

    class ViewModel private constructor(val binding: ListItemArtistBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Artist) {
            binding.artist = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewModel {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ListItemArtistBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.list_item_artist, parent, false
                )

                return ViewModel(binding)
            }
        }
    }
}


class ArtistDiffCallback: DiffUtil.ItemCallback<Artist>() {
    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.artistName == newItem.artistName
    }
}