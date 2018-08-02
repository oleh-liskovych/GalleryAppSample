package com.olehliskovych.picturesgallerysampleapp.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.data.repository.IMainRepository
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.NetworkState
import javax.inject.Inject

class MainViewModel @Inject constructor(repository: IMainRepository) : ViewModel() {

    val networkState: LiveData<NetworkState>?
    val list: LiveData<PagedList<PictureEntity>>

    init {
        networkState = repository.getNetworkState()
        list = repository.getPictures()
    }

}