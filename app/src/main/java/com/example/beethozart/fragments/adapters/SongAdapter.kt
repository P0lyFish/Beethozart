package com.example.beethozart.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beethozart.databinding.ListItemSongBinding
import com.example.beethozart.entities.Song


class SongAdapter(private val clickListener: SongListener): ListAdapter<Song, SongAdapter.ViewHolder>(
    SongDiffCallback()
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemSongBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: SongListener, item: Song) {
            binding.song = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemSongBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class SongDiffCallback: DiffUtil.ItemCallback<Song>() {
    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.songId == newItem.songId
    }
}


class SongListener(val clickListener: (song: Song) -> Unit) {
    fun onClick(song: Song) {
        clickListener(song)
    }
}

