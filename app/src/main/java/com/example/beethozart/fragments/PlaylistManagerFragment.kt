package com.example.beethozart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.beethozart.R
import com.example.beethozart.databinding.FragmentPlaylistManagerBinding
import com.example.beethozart.fragments.adapters.PlaylistAdapter
import com.example.beethozart.viewmodels.PlaylistViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [PlaylistManagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlaylistManagerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPlaylistManagerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_playlist_manager, container, false
        )

        val viewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)
        val adapter = PlaylistAdapter()

        binding.lifecycleOwner = this
        binding.playlistList.adapter = adapter
        binding.playlistViewModel = viewModel

        viewModel.playlistList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        binding.addPlaylistButton.setOnClickListener {
            viewModel.onAddPlaylist(requireActivity())
        }

        return binding.root
    }
}