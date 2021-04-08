package com.example.beethozart.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.beethozart.R
import com.example.beethozart.databases.SongDatabase
import com.example.beethozart.databinding.OnlineHomeBinding
import com.example.beethozart.viewmodels.OnlineHomeViewModel
import com.example.beethozart.viewmodels.factories.OnlineHomeViewModelFactory
import kotlin.properties.Delegates

class OnlineHomeFragment : Fragment() {
    private var isSignIn = true
    private lateinit var viewModel: OnlineHomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: OnlineHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.online_home, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = SongDatabase.getInstance(application).songDatabaseDao

        val viewModelFactory = OnlineHomeViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(OnlineHomeViewModel::class.java)

        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                Log.d("aaa" , "empty")
                isSignIn = false
                activity?.invalidateOptionsMenu()
            }
            if(it.size == 1) {
                Log.d("aaa" , "Co user roi")
                isSignIn = true
                activity?.invalidateOptionsMenu()
            }
        })

        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.online_music_menu, menu)
        val itemLogOut = menu.findItem(R.id.logOut)
        val itemSignUp = menu.findItem(R.id.signUpFragment)
        val itemSignIn = menu.findItem(R.id.signInFragment)
        if (!isSignIn) {
            itemSignIn.isVisible = true
            itemSignUp.isVisible = true
            itemLogOut.isVisible = false
        } else {
            itemSignIn.isVisible = false
            itemSignUp.isVisible = false
            itemLogOut.isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logOut -> {
                viewModel.clearUser()
                isSignIn = false
                activity?.invalidateOptionsMenu()
                true
            }
            else -> {
                NavigationUI.onNavDestinationSelected(
                    item!!,
                    requireView().findNavController()
                )
                        || super.onOptionsItemSelected(item)
            }
        }

    }


}