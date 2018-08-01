package com.olehliskovych.picturesgallerysampleapp.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.PersistableBundle
import com.olehliskovych.picturesgallerysampleapp.R
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity<T : ViewModel> : DaggerAppCompatActivity() {

    protected var viewModel: T? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

}