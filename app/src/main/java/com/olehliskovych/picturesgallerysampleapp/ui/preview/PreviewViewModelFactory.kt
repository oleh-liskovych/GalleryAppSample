package com.olehliskovych.picturesgallerysampleapp.ui.preview

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class PreviewViewModelFactory @Inject
constructor(var model: PreviewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PreviewViewModel(model) as T
    }
}