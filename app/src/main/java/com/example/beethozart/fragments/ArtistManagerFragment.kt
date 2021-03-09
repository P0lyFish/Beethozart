package com.example.beethozart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.beethozart.R
import com.example.beethozart.viewmodels.ArtistViewModel
import com.example.beethozart.databinding.FragmentArtistManagerBinding
import com.example.beethozart.fragments.adapters.ArtistAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [ArtistManagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArtistManagerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentArtistManagerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_artist_manager, container, false
        )
        val viewModel = ViewModelProvider(this).get(ArtistViewModel::class.java)
        val adapter = ArtistAdapter()

        binding.lifecycleOwner = this
        binding.artistList.adapter = adapter

        viewModel.artistList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        return binding.root
    }
}