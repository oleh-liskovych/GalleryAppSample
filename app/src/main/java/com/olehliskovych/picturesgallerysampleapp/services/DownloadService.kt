package com.olehliskovych.picturesgallerysampleapp.services


import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import com.olehliskovych.picturesgallerysampleapp.R
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.UnsplashAPI
import dagger.android.AndroidInjection
import junit.framework.Assert.assertNotNull
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


class DownloadService: IntentService(SERVICE_NAME) {

    private var pictureUrl: String? = null
    private var pictureId: String? = null

    @Inject
    lateinit var service: UnsplashAPI

    companion object {

        private val SERVICE_NAME = DownloadService::class.java.name + ".Download"
        private const val NOTIFICATION_ID = 15

        val PICTURE_URL = DownloadService::class.java.name + ".picture_url"
        val PICTURE_ID = DownloadService::class.java.name + ".picture_id"
        const val ACTION_RESULT = "DownloadService"
        const val ACTION_RESULT_EXTRA = "DownloadService_result"

    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()

        val serviceNotificationChannelId = getString(R.string.notification_channel_id)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(
                    Context.NOTIFICATION_SERVICE) as NotificationManager

            assertNotNull("Notification manager service failed", notificationManager)

            val channel = NotificationChannel(serviceNotificationChannelId,
                    getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT)

            notificationManager.createNotificationChannel(channel)
        }

        startForeground(NOTIFICATION_ID, buildNotification(serviceNotificationChannelId))
    }

    override fun onDestroy() {
        super.onDestroy()

        stopForeground(true)
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            pictureUrl = intent.getStringExtra(PICTURE_URL)
            pictureId = intent.getStringExtra(PICTURE_ID)
            if (!pictureUrl.isNullOrEmpty()
                && !pictureId.isNullOrEmpty()) {
                var success = false
                try {
                    val response = service.getPicture(pictureUrl!!).execute()


                    if (response.isSuccessful) {
                        val bytes = response.body()!!.bytes()

                        val downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        val downloadPath = File(downloadFolder, "$pictureId.jpg")
                        FileOutputStream(downloadPath).use { fos ->
                            fos.write(bytes)
                            fos.flush()
                        }
                        success = true
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    val resultIntent = Intent(ACTION_RESULT)

                    resultIntent.putExtra(ACTION_RESULT_EXTRA, success)

                    LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent)
                }

            }
        }
    }

    private fun buildNotification(channelId: String): Notification {
        return NotificationCompat.Builder(this, channelId)
                .setAutoCancel(false)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setProgress(100, 0, true)
                .setTicker(this.resources.getString(R.string.msg_downloading))
                .setContentTitle(this.resources.getString(R.string.msg_downloading))
                .setContentText(pictureUrl)
                .build()
    }
}