package com.olehliskovych.picturesgallerysampleapp.ui.preview

import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.olehliskovych.picturesgallerysampleapp.R
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.databinding.ActivityPreviewBinding
import dagger.android.support.DaggerAppCompatActivity

class PreviewActivity : DaggerAppCompatActivity() {

    companion object {
        const val sItemExtraKey = "item"
    }

    private lateinit var binding: ActivityPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview)
        setupUI()
    }

    private fun setupUI() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val entity: PictureEntity = intent.extras.getParcelable(sItemExtraKey)
        binding.entity = entity
    }
}