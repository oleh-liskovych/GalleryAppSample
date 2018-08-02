package com.olehliskovych.picturesgallerysampleapp.data.repository

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.NetworkState

interface IMainRepository {
    fun getPictures(query: String = "") : LiveData<PagedList<PictureEntity>>
    fun getNetworkState() : LiveData<NetworkState>?
}