package com.olehliskovych.picturesgallerysampleapp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureEntity(@Expose val id: String,
                         @Expose val width: Int,
                         @Expose val height: Int,
                         @Expose val urls: UrlsEntity,
                         @Expose val user: UserEntity
) : Parcelable