package com.olehliskovych.picturesgallerysampleapp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResultEntity(@Expose val total: Int,
                              @Expose val results: List<PictureEntity>) : Parcelable