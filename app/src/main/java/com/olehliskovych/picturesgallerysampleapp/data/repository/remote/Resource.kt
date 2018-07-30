package com.olehliskovych.picturesgallerysampleapp.data.repository.remote

class Resource<T> private constructor(private val status: State,
                                      private val data: T?,
                                      private val throwable: Throwable?) {


    enum class State {
        LOADING,
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> loading(): Resource<T> {
            return Resource<T>(State.LOADING, null, null)
        }

        fun <T> success(data: T): Resource<T> {
            return Resource(State.SUCCESS, data, null)
        }

        fun <T> error(throwable: Throwable): Resource<T> {
            return Resource(State.ERROR, null, throwable)
        }
    }

}