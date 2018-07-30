package com.olehliskovych.picturesgallerysampleapp.ui.preview

import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.UnsplashAPI
import javax.inject.Inject

class PreviewModelImpl @Inject constructor(val api: UnsplashAPI) : PreviewModel {



    override fun downloadPicture() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}