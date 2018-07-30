package com.olehliskovych.picturesgallerysampleapp.di.global

import com.olehliskovych.picturesgallerysampleapp.di.MainActivityModule
import com.olehliskovych.picturesgallerysampleapp.di.PreviewActivityModule
import com.olehliskovych.picturesgallerysampleapp.di.scopes.ActivityScope
import com.olehliskovych.picturesgallerysampleapp.ui.main.MainActivity
import com.olehliskovych.picturesgallerysampleapp.ui.preview.PreviewActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuildingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun mainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [PreviewActivityModule::class])
    fun previewActivity(): PreviewActivity

}