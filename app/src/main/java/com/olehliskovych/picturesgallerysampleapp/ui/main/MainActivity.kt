package com.olehliskovych.picturesgallerysampleapp.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.olehliskovych.picturesgallerysampleapp.R
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.Resource
import com.olehliskovych.picturesgallerysampleapp.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), PicturesAdapter.PictureClickListener {

    @Inject
    protected lateinit var pictureAdapter: PicturesAdapter

    @Inject
    protected lateinit var factory: MainViewModelFactory

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        setupViewModel()
        setupRecyclerView()
        loadFirst()
//        if (viewModel.currentPagePictures.value != null &&
//                viewModel.currentPagePictures.value!!.data != null &&
//                viewModel.currentPagePictures.value!!.data!!.size == 0) {
//            loadFirst()
//        }
    }

    private fun loadFirst() {
        viewModel.getPhotos()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        val listObserver: Observer<Resource<List<PictureEntity>>> = Observer {
            when(it?.state) {
                Resource.State.SUCCESS -> {
                    if (it.data != null) {
                        pictureAdapter.submitItems(it.data)
                    }
                }
                Resource.State.LOADING -> {
                    // show loading wheel
                }
                Resource.State.ERROR -> {
                    // show error snackbar
                }
            }
        }
        viewModel.currentPagePictures.observe(this, listObserver)
    }

    private fun setupRecyclerView() {
        binding.recycler.adapter = pictureAdapter
    }

    override fun onItemClick(view: View, adapterPos: Int) {
        Toast.makeText(this, "Item: "+adapterPos, Toast.LENGTH_SHORT).show()
    }



}