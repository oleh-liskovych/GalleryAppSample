package com.olehliskovych.picturesgallerysampleapp.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserEntity(@Expose val name: String,
                      @SerializedName("profile_image") val avatar: ProfileImageEntity) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(ProfileImageEntity::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeParcelable(avatar, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserEntity> {
        override fun createFromParcel(parcel: Parcel): UserEntity {
            return UserEntity(parcel)
        }

        override fun newArray(size: Int): Array<UserEntity?> {
            return arrayOfNulls(size)
        }
    }

}

data class ProfileImageEntity(@Expose val small: String,
                              @Expose val medium: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(small)
        parcel.writeString(medium)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfileImageEntity> {
        override fun createFromParcel(parcel: Parcel): ProfileImageEntity {
            return ProfileImageEntity(parcel)
        }

        override fun newArray(size: Int): Array<ProfileImageEntity?> {
            return arrayOfNulls(size)
        }
    }

}