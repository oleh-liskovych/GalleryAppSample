package com.olehliskovych.picturesgallerysampleapp.data.repository.remote

import android.arch.paging.DataSource
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainRemoteDataSourceFactory @Inject constructor(private val service: UnsplashAPI) : DataSource.Factory<Int, PictureEntity>() {
//    val sourceLiveData = MutableLiveData<MainRemoteDataSource>()
    private val datasourceSubject: PublishSubject<MainRemoteDataSource> = PublishSubject.create()

    fun getDatasourceObservable() : Observable<MainRemoteDataSource> {
        return datasourceSubject.hide()
    }
    override fun create(): DataSource<Int, PictureEntity> {
        val source = MainRemoteDataSource(service)
//        sourceLiveData.postValue(source)
        datasourceSubject.onNext(source)
        return source
    }
}