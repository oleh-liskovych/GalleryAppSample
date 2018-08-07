package com.olehliskovych.picturesgallerysampleapp.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.util.Log
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.data.repository.IMainRepository
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.MainRemoteDataSource
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.NetworkState
import javax.inject.Inject

class MainViewModel @Inject constructor(val repository: IMainRepository) : ViewModel() {

    val networkState: LiveData<NetworkState>
    val pagedListLiveData: LiveData<PagedList<PictureEntity>>

    init {
        networkState = repository.getNetworkState()
        pagedListLiveData = repository.getPictures()
    }

    fun newPicturesRequest(query: String = "") {
        repository.newPicturesRequest(query)
    }

}