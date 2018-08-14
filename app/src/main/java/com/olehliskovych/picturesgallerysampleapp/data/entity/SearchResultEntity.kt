package com.olehliskovych.picturesgallerysampleapp.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

data class SearchResultEntity(@Expose val total: Int,
                              @Expose val results: List<PictureEntity>) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.createTypedArrayList(PictureEntity.CREATOR)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(total)
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchResultEntity> {
        override fun createFromParcel(parcel: Parcel): SearchResultEntity {
            return SearchResultEntity(parcel)
        }

        override fun newArray(size: Int): Array<SearchResultEntity?> {
            return arrayOfNulls(size)
        }
    }

}