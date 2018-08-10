package com.olehliskovych.picturesgallerysampleapp.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.olehliskovych.picturesgallerysampleapp.data.repository.IMainRepository
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.MainRepository
import com.olehliskovych.picturesgallerysampleapp.di.scopes.ActivityScope
import com.olehliskovych.picturesgallerysampleapp.main.ui.gallery.*
import com.olehliskovych.picturesgallerysampleapp.main.viewModel.MainViewModel
import com.olehliskovych.picturesgallerysampleapp.main.viewModel.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [])
abstract class MainActivityModule {

    @Module
    companion object {
        @JvmStatic
        @ActivityScope
        @Provides
        fun providePicturesAdapter(mainActivity: MainActivity) : PicturesAdapter {
            return PicturesAdapter(mainActivity)
        }
    }

    @ActivityScope
    @Binds
    abstract fun bindViewModelProviderFactory(factory: MainViewModelFactory) : ViewModelProvider.Factory

    @ActivityScope
    @Binds
    abstract fun bindMainRepository(mainRepository: MainRepository) : IMainRepository

    @ActivityScope
    @Binds
    abstract fun bindMainViewModel(mainViewModel: MainViewModel) : ViewModel

}