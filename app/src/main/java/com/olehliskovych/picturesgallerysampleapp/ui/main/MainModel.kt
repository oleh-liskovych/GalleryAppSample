package com.olehliskovych.picturesgallerysampleapp.ui.main

import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.ui.base.BaseModel

interface MainModel : BaseModel {
    suspend fun getPhotos(page: Int, count: Int) : List<PictureEntity>
    suspend fun getSearchedPhotos(query: String, page: Int, count: Int) : List<PictureEntity>
}