package com.olehliskovych.picturesgallerysampleapp.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

data class PictureEntity(@Expose val id: String,
                         @Expose val width: Int,
                         @Expose val height: Int,
                         @Expose val urls: UrlsEntity,
                         @Expose val user: UserEntity
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readParcelable(UrlsEntity::class.java.classLoader),
            parcel.readParcelable(UserEntity::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeParcelable(urls, flags)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "PictureEntity(id='$id', width=$width, height=$height, urls=$urls, user=$user)"
    }

    companion object CREATOR : Parcelable.Creator<PictureEntity> {
        override fun createFromParcel(parcel: Parcel): PictureEntity {
            return PictureEntity(parcel)
        }

        override fun newArray(size: Int): Array<PictureEntity?> {
            return arrayOfNulls(size)
        }
    }


}