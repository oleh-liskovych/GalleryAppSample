package com.olehliskovych.picturesgallerysampleapp.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.Resource
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

class MainViewModel @Inject constructor(private var model: MainModel) : ViewModel() {

    private var page: Int = 1
    private var count: Int = 20
    var query: String = ""

    //    private var wholePictureList: List<PictureEntity> = ArrayList()
    var currentPagePictures: MutableLiveData<Resource<List<PictureEntity>>> = MutableLiveData()

    fun getPhotos(){
        currentPagePictures.postValue(Resource.loading())
        async {
            try {
                val list = model.getPhotos(page, count)
                currentPagePictures.postValue(Resource.success(list))
            } catch (e: Exception) {
                currentPagePictures.postValue(Resource.error(e))
            }
        }
    }

    fun getSearchedPhotos(){
        currentPagePictures.postValue(Resource.loading())
        async {
            try {
                val list = model.getSearchedPhotos(query, page, count)
                Resource.success(list)
                currentPagePictures.postValue(Resource.success(list))
            } catch (e: Exception) {
                currentPagePictures.postValue(Resource.error(e))
            }
        }
    }

}