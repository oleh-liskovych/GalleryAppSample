package com.olehliskovych.picturesgallerysampleapp.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.olehliskovych.picturesgallerysampleapp.di.scopes.ActivityScope
import com.olehliskovych.picturesgallerysampleapp.ui.main.*
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
    abstract fun bindMainModel(mainModel: MainModelImpl) : MainModel

    @ActivityScope
    @Binds
    abstract fun bindMainViewModel(mainViewModel: MainViewModel) : ViewModel

}