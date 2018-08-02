package com.olehliskovych.picturesgallerysampleapp.data.repository.remote

class NetworkState(val state: NetworkState.State,
                   val throwable: Throwable? = null) {

    enum class State {
        LOADING,
        SUCCEEDED,
        FAILED
    }

    companion object {
        fun loading(): NetworkState {
            return NetworkState(NetworkState.State.LOADING)
        }

        fun success(): NetworkState {
            return NetworkState(NetworkState.State.SUCCEEDED)
        }

        fun error(throwable: Throwable): NetworkState {
            return NetworkState(NetworkState.State.FAILED, throwable)
        }
    }
}