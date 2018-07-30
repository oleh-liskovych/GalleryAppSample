package com.olehliskovych.picturesgallerysampleapp.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class MainViewModelFactory @Inject
constructor(var model: MainModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(model) as T
    }
}