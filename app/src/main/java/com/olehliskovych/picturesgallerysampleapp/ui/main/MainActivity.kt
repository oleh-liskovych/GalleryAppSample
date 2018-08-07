package com.olehliskovych.picturesgallerysampleapp.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager
import com.olehliskovych.picturesgallerysampleapp.R
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.data.repository.remote.NetworkState
import com.olehliskovych.picturesgallerysampleapp.databinding.ActivityMainBinding
import com.olehliskovych.picturesgallerysampleapp.ui.preview.PreviewActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.toolbar_search.view.*
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
        setupUI()
    }

    private fun setupUI() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setupViewModel()
        setupRecyclerView()
        handleNetworkState()
        setupSearch()
        setupPullToRefresh()
    }

    private fun setupPullToRefresh() {
        binding.swipeContainer.setOnRefreshListener {
            viewModel.newPicturesRequest(binding.toolbar.search.text.toString())
        }
        binding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private fun setupSearch() {
        binding.toolbar.getSearchLiveData().observe(this, Observer<String> {
            t -> t?.let { viewModel.newPicturesRequest(t) }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        viewModel.pagedListLiveData.observe(this, Observer<PagedList<PictureEntity>> {
            pictureAdapter.submitList(it)
        })
    }

    private fun handleNetworkState() {
        viewModel.networkState.observe(this, Observer<NetworkState> {
            val emptyListLabelVisibility = if (it != null && it.state == NetworkState.State.EMPTY) View.VISIBLE else View.GONE
            binding.emptyListLabel.visibility = emptyListLabelVisibility

            val loadingProgressBarVisibility = if (it != null
                    && it.state == NetworkState.State.LOADING
                    && !binding.swipeContainer.isRefreshing) View.VISIBLE else View.GONE
            binding.progress.visibility = loadingProgressBarVisibility

            if (it != null
                    && it.state != NetworkState.State.LOADING
                    && binding.swipeContainer.isRefreshing) {
                binding.swipeContainer.isRefreshing = false
            }

            if (it != null && it.state == NetworkState.State.ERROR) {
                Snackbar.make(binding.root, R.string.error_message, Snackbar.LENGTH_LONG)
                        .setAction(R.string.error_action) {
                            viewModel.newPicturesRequest(binding.toolbar.search.text.toString())
                        }
                        .setActionTextColor(ContextCompat.getColor(this, android.R.color.holo_orange_dark))
                        .show()
            }
        })
    }

    private fun setupRecyclerView() {
        binding.recycler.adapter = pictureAdapter

    }

    override fun onItemClick(view: View, item: PictureEntity) {
        val intent = Intent(this, PreviewActivity::class.java)
        intent.putExtra(PreviewActivity.sItemExtraKey, item)
        startActivity(intent)
    }



}