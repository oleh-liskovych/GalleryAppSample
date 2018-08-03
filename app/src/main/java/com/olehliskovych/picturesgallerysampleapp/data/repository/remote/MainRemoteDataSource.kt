package com.olehliskovych.picturesgallerysampleapp.data.repository.remote

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

class MainRemoteDataSource @Inject constructor(
        private val service: UnsplashAPI
)  : PageKeyedDataSource<Int, PictureEntity>() {

    val networkState : MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PictureEntity>) {
        networkState.postValue(NetworkState(NetworkState.State.LOADING))
        async {
            try {
                val list = service.getPhotos(1, params.requestedLoadSize)
                val response = list.execute()
                if (response != null && response.isSuccessful && response.body() != null) {
                    networkState.postValue(NetworkState(NetworkState.State.SUCCEEDED))
                    print("INITIAL ITEMS RECEIVED: " + response.body()!!.size)
                    callback.onResult(response.body()!!, 1, 2)
                }
            } catch (e: Exception) {
                networkState.postValue(NetworkState(NetworkState.State.FAILED, e))
                e.printStackTrace()
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PictureEntity>) {
        networkState.postValue(NetworkState(NetworkState.State.LOADING))
        async {
            try {
                val list = service.getPhotos(params.key, params.requestedLoadSize)
                val response = list.execute()
                if (response != null && response.isSuccessful && response.body() != null) {
                    networkState.postValue(NetworkState(NetworkState.State.SUCCEEDED))
                    print("loadAfter ITEMS RECEIVED: " + response.body()!!.size)
                    callback.onResult(response.body()!!, params.key + 1)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PictureEntity>) {
//        networkState.postValue(NetworkState(NetworkState.State.LOADING))
//        async {
//            try {
//                val list = service.getPhotos(params.key, params.requestedLoadSize)
//                val response = list.execute()
//                if (response != null && response.isSuccessful && response.body() != null) {
//                    networkState.postValue(NetworkState(NetworkState.State.FAILED))
//                    print("loadBefore ITEMS RECEIVED: " + response.body()!!.size)
//                    callback.onResult(response.body()!!, params.key - 1)
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
    }

}