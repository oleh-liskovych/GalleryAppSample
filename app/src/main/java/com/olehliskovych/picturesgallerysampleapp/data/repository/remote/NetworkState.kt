package com.olehliskovych.picturesgallerysampleapp.data.repository.remote

class NetworkState(val state: NetworkState.State,
                   val throwable: Throwable? = null) {

    enum class State {
        LOADING,
        SUCCESS,
        EMPTY,
        ERROR
    }

    override fun toString(): String {
        return state.name
    }

    companion object {
        fun loading(): NetworkState {

            return NetworkState(NetworkState.State.LOADING)
        }

        fun success(): NetworkState {
            return NetworkState(NetworkState.State.SUCCESS)
        }

        fun empty(): NetworkState {
            return NetworkState(NetworkState.State.EMPTY)
        }

        fun error(throwable: Throwable): NetworkState {
            return NetworkState(NetworkState.State.ERROR, throwable)
        }
    }
}