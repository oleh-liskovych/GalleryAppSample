package com.olehliskovych.picturesgallerysampleapp.ui.main

import android.os.Bundle
import com.olehliskovych.picturesgallerysampleapp.R
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.UnsplashAPI
import dagger.android.support.DaggerAppCompatActivity
import retrofit2.Call
import javax.inject.Inject
import retrofit2.Callback
import retrofit2.Response

class MainActivity : DaggerAppCompatActivity(), Callback<List<PictureEntity>> {
    override fun onFailure(call: Call<List<PictureEntity>>?, t: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResponse(call: Call<List<PictureEntity>>?, response: Response<List<PictureEntity>>?) {

        val list = response?.body()
        val iterator = list?.iterator()
        iterator?.forEach {
            println("$it \n")
        }

    }

    @Inject lateinit var unsplashApi: UnsplashAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val response = unsplashApi.getPhotos(1, 10)
        response.enqueue(this)

    }


}