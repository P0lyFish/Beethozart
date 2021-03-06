package com.example.beethozart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.beethozart.MainActivity
import com.example.beethozart.R
import com.example.beethozart.databinding.FragmentPlayerBinding
import com.example.beethozart.viewmodels.PlayerViewModel
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_main.*


/**
 * A simple [Fragment] subclass.
 * Use the [PlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPlayerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_player, container, false
        )
        binding.lifecycleOwner = this

        val args = PlayerFragmentArgs.fromBundle(requireArguments())

        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.hide()
        mainActivity.findViewById<MaterialCardView>(R.id.miniPlayer).visibility = View.GONE

        val viewModel = ViewModelProvider(activity!!).get(
            PlayerViewModel::class.java)
        viewModel.playSongList(args.songList)

        binding.viewModel = viewModel

        binding.elapsedTimeSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser)
                viewModel.onSeek(value)
        }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()

        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.show()
    }

    override fun onDestroy() {
        super.onDestroy()

        val mainActivity = activity as MainActivity
        mainActivity.findViewById<MaterialCardView>(R.id.miniPlayer).visibility = View.VISIBLE
    }
}