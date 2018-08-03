package com.olehliskovych.picturesgallerysampleapp.data.repository.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.data.repository.IMainRepository
import javax.inject.Inject

class MainRepository @Inject constructor(
        private val sourceFactory: MainRemoteDataSourceFactory
) : IMainRepository {

    val config = PagedList.Config.Builder()
            .setPageSize(15)
            .setInitialLoadSizeHint(15)
            .setPrefetchDistance(12)
            .setEnablePlaceholders(false)
            .build()

    override fun getPictures(query: String): LiveData<PagedList<PictureEntity>> {
        return LivePagedListBuilder(
                        sourceFactory,
                        config)
                        .build()
    }

    override fun getNetworkState(): MutableLiveData<NetworkState>? {
        return sourceFactory.sourceLiveData.value?.networkState // todo: replaced this with observe after moving to viewmodel
    }

}