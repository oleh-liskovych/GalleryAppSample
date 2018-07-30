package com.olehliskovych.picturesgallerysampleapp.data.repository.remote.interceptors

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ClientIdInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request: Request = chain!!.request()
        val url: HttpUrl = request.url()
                .newBuilder()
                .addQueryParameter("client_id", CLIENT_ID)
                .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {
        private const val CLIENT_ID = "215b9844ff8cce2d7979c28dcd9193a7d5cd8caccc379fc9bea8c5e183f344b1"
    }
}