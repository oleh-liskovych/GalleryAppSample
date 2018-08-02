package com.olehliskovych.picturesgallerysampleapp.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.Toast
import com.olehliskovych.picturesgallerysampleapp.R
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
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

    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        viewModel.list.observe(this, Observer<PagedList<PictureEntity>> {
            pictureAdapter.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        binding.recycler.adapter = pictureAdapter
        binding.swipeContainer.setOnRefreshListener { SwipeRefreshLayout.OnRefreshListener {

        }
        }
    }

    override fun onItemClick(view: View, adapterPos: Int) {
        Toast.makeText(this, "Item: "+adapterPos, Toast.LENGTH_SHORT).show()
    }



}