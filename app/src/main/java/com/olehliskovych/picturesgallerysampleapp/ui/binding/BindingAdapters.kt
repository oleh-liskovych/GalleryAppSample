package com.olehliskovych.picturesgallerysampleapp.ui.binding

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    if (!TextUtils.isEmpty(url)) {
        Glide.with(context)
                .load(url)
                .into(this)
    } else {
        this.setImageDrawable(null)
    }
}

@BindingAdapter("avatarUrl")
fun ImageView.setAvatarUrl(url: String?) {
    if (!TextUtils.isEmpty(url)) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(this)
    } else {
        this.setImageDrawable(null)
    }
}