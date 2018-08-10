package com.olehliskovych.picturesgallerysampleapp.data.repository.remote

import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.data.entity.SearchResultEntity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UnsplashAPI {

    companion object {
        const val BASE_URL = "https://api.unsplash.com"
    }

    @GET("/photos")
    fun getPhotos(@Query("page") page: Int?,
                  @Query("per_page") count: Int?,
                  @Query("order_by") order_by: String = "popular") : Call<List<PictureEntity>>

    @GET("/search/photos")
    fun getSearchedPhotos(@Query("query") query: String,
                          @Query("page") page: Int?,
                          @Query("per_page") count: Int?) : Call<SearchResultEntity>

    @GET
    fun getPicture(@Url url: String) : Call<ResponseBody>
}