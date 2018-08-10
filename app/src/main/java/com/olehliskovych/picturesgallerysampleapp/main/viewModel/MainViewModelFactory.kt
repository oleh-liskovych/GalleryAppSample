package com.olehliskovych.picturesgallerysampleapp.main.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.MainRepository
import javax.inject.Inject

class MainViewModelFactory @Inject
constructor(var repo: MainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}