package com.olehliskovych.picturesgallerysampleapp.ui.binding

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.olehliskovych.picturesgallerysampleapp.global.SERVER_URL


@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, imageUrl: String) {
    if (!TextUtils.isEmpty(imageUrl)) {
        Glide.with(view.context)
                .load(SERVER_URL + imageUrl)
                .into(view)
    } else {
        view.setImageDrawable(null)
    }
}