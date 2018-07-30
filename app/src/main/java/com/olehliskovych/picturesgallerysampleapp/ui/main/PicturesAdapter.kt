package com.olehliskovych.picturesgallerysampleapp.ui.main

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.databinding.ItemPictureListBinding

import java.util.*
import javax.inject.Inject

class PicturesAdapter @Inject constructor() : RecyclerView.Adapter<PicturesAdapter.ItemViewHolder>() {

    private var asyncDiffer: AsyncListDiffer<PictureEntity>



    init {
        this.asyncDiffer = AsyncListDiffer<PictureEntity>(this, DIFF_CALLBACK)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<PictureEntity> = object : DiffUtil.ItemCallback<PictureEntity>() {
            override fun areItemsTheSame(oldItem: PictureEntity, newItem: PictureEntity): Boolean {
                return oldItem.id.equals(newItem.id)
            }

            override fun areContentsTheSame(oldItem: PictureEntity, newItem: PictureEntity): Boolean {
                return Objects.equals(oldItem, newItem)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class ItemViewHolder(private val binding: ItemPictureListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(picture : PictureEntity) {
            binding.setPictureEntity(picture)
            binding.executePendingBindings()
        }

    }
}