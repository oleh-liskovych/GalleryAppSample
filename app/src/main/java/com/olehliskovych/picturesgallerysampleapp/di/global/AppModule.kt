package com.olehliskovych.picturesgallerysampleapp.di.global

import android.content.Context
import com.olehliskovych.picturesgallerysampleapp.App
import com.olehliskovych.picturesgallerysampleapp.di.scopes.AppScope
import dagger.Binds
import dagger.Module

@Module
interface AppModule {
    @AppScope
    @Binds
    fun bindContext(app: App): Context


}