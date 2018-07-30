package com.olehliskovych.picturesgallerysampleapp.ui.base

import android.arch.lifecycle.ViewModel

abstract class BaseViewModel<T : BaseModel> protected constructor(protected val model: T) : ViewModel() {


}