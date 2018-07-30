package com.olehliskovych.picturesgallerysampleapp.data.repository.remote.interceptors

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AttributionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request: Request = chain!!.request()
        val url: HttpUrl = request.url()
                .newBuilder()
                .addQueryParameter("utm_source", APP_NAME_ON_UNSPLAS)
                .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)

    }
    companion object {
        private const val APP_NAME_ON_UNSPLAS = "MySampleApp"
    }
}