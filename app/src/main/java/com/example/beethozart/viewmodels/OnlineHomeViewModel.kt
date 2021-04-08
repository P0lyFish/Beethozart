package com.example.beethozart.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.beethozart.databases.daos.SongDatabaseDao
import kotlinx.coroutines.*

class OnlineHomeViewModel(private val database: SongDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var currentUser = database.getUser()

    fun clearUser() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.deleteUser()
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}