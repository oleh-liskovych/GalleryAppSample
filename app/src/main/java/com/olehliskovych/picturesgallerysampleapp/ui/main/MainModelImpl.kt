package com.olehliskovych.picturesgallerysampleapp.ui.main

import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.UnsplashAPI
import java.util.*
import javax.inject.Inject

class MainModelImpl @Inject constructor(val api: UnsplashAPI) : MainModel {

    override suspend fun getPhotos(page: Int, count: Int): List<PictureEntity> {
        return api.getPhotos(page, count)
                .execute()
                .body()?.let { it } ?: Collections.emptyList<PictureEntity>()
    }

    override suspend fun getSearchedPhotos(query: String, page: Int, count: Int): List<PictureEntity> {
        return api.getSearchedPhotos(query, page, count)
                .execute()
                .body()?.let { it } ?: Collections.emptyList<PictureEntity>()
    }

}