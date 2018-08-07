package com.olehliskovych.picturesgallerysampleapp.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

data class UrlsEntity(@Expose val raw: String,
                      @Expose val full: String,
                      @Expose val regular: String,
                      @Expose val thumb: String) : Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(raw)
        parcel.writeString(full)
        parcel.writeString(regular)
        parcel.writeString(thumb)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UrlsEntity> {
        override fun createFromParcel(parcel: Parcel): UrlsEntity {
            return UrlsEntity(parcel)
        }

        override fun newArray(size: Int): Array<UrlsEntity?> {
            return arrayOfNulls(size)
        }
    }


}