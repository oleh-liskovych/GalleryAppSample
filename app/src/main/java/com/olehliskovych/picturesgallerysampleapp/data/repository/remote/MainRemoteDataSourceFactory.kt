package com.olehliskovych.picturesgallerysampleapp.data.repository.remote

import android.arch.paging.DataSource
import android.util.Log
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainRemoteDataSourceFactory @Inject constructor(private val service: UnsplashAPI) : DataSource.Factory<Int, PictureEntity>() {
    private val datasourceSubject: BehaviorSubject<MainRemoteDataSource> = BehaviorSubject.create()
    private var query: String = ""

    fun newPicturesRequest(query: String) {
        this.query = query
        datasourceSubject.value.invalidate()
    }

    fun getDatasourceObservable() : Observable<MainRemoteDataSource> {
        return datasourceSubject.hide()
    }
    override fun create(): DataSource<Int, PictureEntity> {
        val source = MainRemoteDataSource(service, query)
        datasourceSubject.onNext(source)
        return source
    }
}