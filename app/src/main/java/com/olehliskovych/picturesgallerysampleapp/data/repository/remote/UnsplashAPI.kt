package com.olehliskovych.picturesgallerysampleapp.data.repository.remote

import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.data.entity.SearchResultEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashAPI {

    companion object {
        val BASE_URL = "https://api.unsplash.com"
    }


    @GET("/photos")
    fun getPhotos(@Query("page") page: Int?,
                  @Query("per_page") count: Int?,
                  @Query("order_by") order_by: String = "popular") : Call<List<PictureEntity>>

    @GET("/search/photos")
    fun getSearchedPhotos(@Query("query") query: String,
                          @Query("page") page: Int?,
                          @Query("per_page") count: Int?) : Call<SearchResultEntity>
}