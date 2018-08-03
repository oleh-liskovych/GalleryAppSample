package com.olehliskovych.picturesgallerysampleapp.di.global

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.UnsplashAPI
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.interceptors.AttributionInterceptor
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.interceptors.ClientIdInterceptor
import com.olehliskovych.picturesgallerysampleapp.di.scopes.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    @JvmStatic
    @AppScope
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .create()
    }

    @JvmStatic
    @AppScope
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(provideClientIdInterceptor())
                .addInterceptor(provideAttributionInterceptor())
                .addInterceptor(provideLoggingInterceptor())
                .build();
    }

    @JvmStatic
    @AppScope
    @Provides
    fun provideRetrofit(baseUrl: String,
                        gson: Gson,
                        client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // remove if is not needed
    }

    @JvmStatic
    @AppScope
    @Provides
    fun provideUnsplashApi(): UnsplashAPI {
        return provideRetrofit(UnsplashAPI.BASE_URL,
                provideGson(),
                provideOkHttpClient())
                .create(UnsplashAPI::class.java)
    }

    @JvmStatic
    @AppScope
    @Provides
    fun provideClientIdInterceptor(): ClientIdInterceptor {
        return ClientIdInterceptor()
    }

    @JvmStatic
    @AppScope
    @Provides
    fun provideAttributionInterceptor() : AttributionInterceptor {
        return AttributionInterceptor()
    }

    @JvmStatic
    @AppScope
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

}