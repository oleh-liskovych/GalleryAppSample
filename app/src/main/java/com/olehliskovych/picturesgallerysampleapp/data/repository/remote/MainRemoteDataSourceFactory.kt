package com.olehliskovych.picturesgallerysampleapp.data.repository.remote

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import javax.inject.Inject

class MainRemoteDataSourceFactory @Inject constructor(private val service: UnsplashAPI) : DataSource.Factory<Int, PictureEntity>() {
    val sourceLiveData = MutableLiveData<MainRemoteDataSource>()
    override fun create(): DataSource<Int, PictureEntity> {
        val source = MainRemoteDataSource(service)
        sourceLiveData.postValue(source)
        return source
    }
}