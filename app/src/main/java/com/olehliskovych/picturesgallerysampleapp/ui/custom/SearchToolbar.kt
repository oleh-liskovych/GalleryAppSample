package com.olehliskovych.picturesgallerysampleapp.ui.custom

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.olehliskovych.picturesgallerysampleapp.databinding.ToolbarSearchBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchToolbar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val searchTextLiveData : MutableLiveData<String> = MutableLiveData()
    private val searchSubject: PublishSubject<String> = PublishSubject.create()
    private var disposable: Disposable? = null
    private val binding: ToolbarSearchBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ToolbarSearchBinding.inflate(inflater, this, true)
        binding.myView = this
        setupUI()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        disposable = searchSubject.debounce(1000, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { string ->
                    if (string != null) {
                        searchTextLiveData.postValue(string.trim())
                    }
                }
    }

    private fun setupUI() {
        binding.search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty() && binding.clearInput.visibility == GONE) {
                    binding.clearInput.visibility = View.VISIBLE
                } else if (p0.toString().isEmpty() && binding.clearInput.visibility == View.VISIBLE) {
                    binding.clearInput.visibility = View.GONE
                }
                searchSubject.onNext(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    fun clearSearchInput() {
        binding.search.setText("")
    }

    fun getSearchLiveData() = searchTextLiveData


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposable?.dispose()
    }
}

