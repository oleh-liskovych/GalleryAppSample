package com.olehliskovych.picturesgallerysampleapp.ui.binding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

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

@BindingAdapter("bigImageUrl", "progress")
fun ImageView.setImageUrlWithProgress(url: String?, progress: ProgressBar) {
    if (!TextUtils.isEmpty(url)) {
        Glide.with(context)
                .load(url)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }

                })
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