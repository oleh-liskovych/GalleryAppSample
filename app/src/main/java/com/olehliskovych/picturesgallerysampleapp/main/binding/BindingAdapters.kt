package com.olehliskovych.picturesgallerysampleapp.main.binding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.design.widget.FloatingActionButton
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
import com.olehliskovych.picturesgallerysampleapp.R

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

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(visible: Boolean) {
    visibility = if(visible) View.VISIBLE else View.GONE
}

@BindingAdapter("fileExists")
fun FloatingActionButton.fileExists(fileExists: Boolean) {
    val drawableRes = if(fileExists) R.drawable.ic_wallpaper else R.drawable.ic_download
    setImageResource(drawableRes)
}