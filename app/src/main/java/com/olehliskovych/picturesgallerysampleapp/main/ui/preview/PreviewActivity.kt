package com.olehliskovych.picturesgallerysampleapp.main.ui.preview

import android.Manifest
import android.app.WallpaperManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.view.WindowManager
import com.olehliskovych.picturesgallerysampleapp.R
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.databinding.ActivityPreviewBinding
import com.olehliskovych.picturesgallerysampleapp.services.DownloadService
import com.olehliskovych.picturesgallerysampleapp.services.DownloadService.Companion.ACTION_RESULT
import com.olehliskovych.picturesgallerysampleapp.services.DownloadService.Companion.ACTION_RESULT_EXTRA
import dagger.android.support.DaggerAppCompatActivity
import java.io.File
import java.io.FileInputStream


class PreviewActivity : DaggerAppCompatActivity() {

    private lateinit var entity: PictureEntity
    private var showButton: Boolean = true
    private var fileExists: Boolean = false
    private var wallpaperExists: Boolean = false

    companion object {
        const val sItemExtraKey = "item"
        private const val SHOW_BUTTON_STATE = "showButtonState"
        private const val WALLPAPER_EXISTENCE_STATE = "wallpapperExistsState"
        private const val PERMISSION_WRITE_EXTERNAL_STORAGE = 443
    }

    private lateinit var binding: ActivityPreviewBinding

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SHOW_BUTTON_STATE, showButton)
        outState.putBoolean(WALLPAPER_EXISTENCE_STATE, wallpaperExists)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview)

        entity = intent.extras.getParcelable(sItemExtraKey)

        setupUI(savedInstanceState)

        LocalBroadcastManager.getInstance(this).registerReceiver(downloadServiceResultReceiver,
                IntentFilter(ACTION_RESULT));
    }

    private fun setupUI(state: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding.entity = entity
        wallpaperExists = state?.getBoolean(WALLPAPER_EXISTENCE_STATE, wallpaperExists) ?: wallpaperExists
        showButton = state?.getBoolean(SHOW_BUTTON_STATE, showButton) ?: showButton
        binding.showButton = showButton
        fileExists = checkFileExistanceInDownLoadFolder()
        binding.fileExists = fileExists

        binding.image.setOnClickListener {
            toggleButtonVisibility()
        }
    }

    private fun checkFileExistanceInDownLoadFolder() : Boolean {
        return getFile().exists()
    }

    private fun getFile() : File {
        val downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        return File(downloadFolder, "${entity.id}.jpg")
    }

    private fun toggleButtonVisibility() {
        if (!wallpaperExists) {
            showButton = !showButton
            binding.showButton = showButton
        }
    }

    private val downloadServiceResultReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(ACTION_RESULT_EXTRA, false)
            if (success) {
                val snack = Snackbar.make(binding.root, R.string.msg_download_completed, Snackbar.LENGTH_LONG)
                        .setAction(R.string.success_action_preview) {
                            setAsWallpaper()
                        }
                        .setActionTextColor(ContextCompat.getColor(this@PreviewActivity, android.R.color.holo_green_light))
                displaySnackBarWithBottomMargin(snack)

                fileExists = checkFileExistanceInDownLoadFolder()
                binding.fileExists = fileExists

            } else {
                val snack = Snackbar.make(binding.root, R.string.error_message, Snackbar.LENGTH_LONG)
                        .setAction(R.string.error_action_preview) {
                            startDownloading()
                        }
                        .setActionTextColor(ContextCompat.getColor(this@PreviewActivity, android.R.color.holo_orange_dark))
                displaySnackBarWithBottomMargin(snack)
            }
        }
    }

    fun buttonClick(view: View) {
        if (fileExists)
            setAsWallpaper()
        else
            startDownloading()
    }

    private fun setAsWallpaper() {
        WallpaperManager.getInstance(this).setStream(FileInputStream(getFile()))
        wallpaperExists = true
        showButton = false
        binding.showButton = showButton
    }

    private fun startDownloading() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(this, DownloadService::class.java)
            intent.putExtra(DownloadService.PICTURE_ID, entity.id)
            intent.putExtra(DownloadService.PICTURE_URL, entity.urls.raw)
            ContextCompat.startForegroundService(this, intent)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_WRITE_EXTERNAL_STORAGE)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownloading()
                } else {
                    val snack = Snackbar.make(binding.root, R.string.msg_downloading_permission_denied, Snackbar.LENGTH_LONG)
                    displaySnackBarWithBottomMargin(snack)
                }
            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(downloadServiceResultReceiver);
        super.onDestroy()
    }

    private fun displaySnackBarWithBottomMargin(snackbar: Snackbar) {
        val snackBarView = snackbar.view
        val navigationBarHeight = resources.getDimension(R.dimen.navigation_bar_height)
        val params = snackBarView.layoutParams as CoordinatorLayout.LayoutParams

        params.setMargins(params.leftMargin + 0,
                params.topMargin,
                params.rightMargin + 0,
                params.bottomMargin + navigationBarHeight.toInt())

        snackBarView.layoutParams = params
        snackbar.show()
    }
}