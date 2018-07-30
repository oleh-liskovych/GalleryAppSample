package com.olehliskovych.picturesgallerysampleapp.di

import com.olehliskovych.picturesgallerysampleapp.App
import com.olehliskovych.picturesgallerysampleapp.di.global.ActivityBuildingModule
import com.olehliskovych.picturesgallerysampleapp.di.global.AppModule
import com.olehliskovych.picturesgallerysampleapp.di.global.NetworkModule
import com.olehliskovych.picturesgallerysampleapp.di.scopes.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(modules = [
    AppModule::class,
    ActivityBuildingModule::class,
    AndroidSupportInjectionModule::class,
    NetworkModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface AppComponentBuilder {
        @BindsInstance
        fun application(app: App): AppComponentBuilder

        fun build(): AppComponent
    }

    override fun inject(instance: App)
}