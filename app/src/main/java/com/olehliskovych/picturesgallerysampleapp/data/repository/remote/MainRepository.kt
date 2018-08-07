package com.olehliskovych.picturesgallerysampleapp.data.repository.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.data.repository.IMainRepository
import javax.inject.Inject

class MainRepository @Inject constructor(
        private val sourceFactory: MainRemoteDataSourceFactory
) : IMainRepository {

    private val mediatorNetworkState = MediatorLiveData<NetworkState>()
    private var currentNetworkStateLiveData : LiveData<NetworkState>? = null

    init {
        observeNetworkState()
    }

    val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .setPrefetchDistance(15)
            .setEnablePlaceholders(false)
            .build()

    override fun getPictures(query: String): LiveData<PagedList<PictureEntity>> {
        return LivePagedListBuilder(
                        sourceFactory,
                        config)
                        .build()
    }

    override fun getNetworkState(): LiveData<NetworkState> {
        return mediatorNetworkState
    }

    private fun observeNetworkState() {
        sourceFactory.getDatasourceObservable().doOnNext {
            item -> if (currentNetworkStateLiveData != null) {
                mediatorNetworkState.removeSource(currentNetworkStateLiveData!!)
            }
            currentNetworkStateLiveData = item.networkState
            mediatorNetworkState.addSource(currentNetworkStateLiveData!!) {
                mediatorNetworkState.postValue(it)
            }
        }.subscribe()
    }

    override fun newPicturesRequest(query: String) {
        sourceFactory.newPicturesRequest(query)
        if (currentNetworkStateLiveData != null) {
                mediatorNetworkState.removeSource(currentNetworkStateLiveData!!)
        }
    }

}