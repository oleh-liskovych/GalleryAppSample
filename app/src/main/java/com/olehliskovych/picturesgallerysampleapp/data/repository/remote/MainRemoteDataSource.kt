package com.olehliskovych.picturesgallerysampleapp.data.repository.remote

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import kotlinx.coroutines.experimental.async
import retrofit2.Call
import javax.inject.Inject

class MainRemoteDataSource @Inject constructor(
        private val service: UnsplashAPI,
        private var query: String
)  : PageKeyedDataSource<Int, PictureEntity>() {

    val networkState : MutableLiveData<NetworkState> = MutableLiveData()

    init {
        networkState.postValue(NetworkState.empty())
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PictureEntity>) {
        networkState.postValue(NetworkState.loading())
        async {
            try {
                if (query.isEmpty()) {
                    val listCall = service.getPhotos(1, params.requestedLoadSize)
                    val response = listCall.execute()
                    if (response != null && response.isSuccessful && response.body() != null) {
                        if (response.body()!!.isEmpty()) {
                            networkState.postValue(NetworkState.empty())
                        } else {
                            networkState.postValue(NetworkState.success())
                        }
                        callback.onResult(response.body()!!, 0, 2)
                    }
                } else {
                    val searchCall = service.getSearchedPhotos(query, 1, params.requestedLoadSize)
                    val response = searchCall.execute()
                    if (response != null && response.isSuccessful && response.body() != null) {
                        if (response.body()!!.results.isEmpty()) {
                            networkState.postValue(NetworkState.empty())
                        } else {
                            networkState.postValue(NetworkState.success())
                        }
                        callback.onResult(response.body()!!.results, 1, 2)
                    }
                }
            } catch (e: Exception) {
                networkState.postValue(NetworkState.error(e))
                e.printStackTrace()
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PictureEntity>) {
        networkState.postValue(NetworkState.loading())
        async {
            try {
                if (query.isEmpty()) {
                    val listCall = service.getPhotos(params.key, params.requestedLoadSize)
                    val response = listCall.execute()
                    if (response != null && response.isSuccessful && response.body() != null) {

                        networkState.postValue(NetworkState.success())

                        callback.onResult(response.body()!!, params.key + 1)
                    }
                } else {
                    val searchCall = service.getSearchedPhotos(query, params.key, params.requestedLoadSize)
                    val response = searchCall.execute()
                    if (response != null && response.isSuccessful && response.body() != null) {

                        networkState.postValue(NetworkState.success())

                        callback.onResult(response.body()!!.results, params.key + 1)
                    }
                }

            } catch (e: Exception) {
                networkState.postValue(NetworkState.error(e))
                e.printStackTrace()
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PictureEntity>) {}

}