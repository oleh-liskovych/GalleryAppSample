package com.olehliskovych.picturesgallerysampleapp.ui.main

import android.databinding.DataBindingUtil
import android.support.constraint.ConstraintSet
import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olehliskovych.picturesgallerysampleapp.R
import com.olehliskovych.picturesgallerysampleapp.data.entity.PictureEntity
import com.olehliskovych.picturesgallerysampleapp.databinding.ItemPictureListBinding

import java.util.*
import javax.inject.Inject

class PicturesAdapter @Inject constructor(private val listener: PictureClickListener) : RecyclerView.Adapter<PicturesAdapter.ItemViewHolder>() {

    private var asyncDiffer: AsyncListDiffer<PictureEntity>

    interface PictureClickListener {
        fun onItemClick(view: View, adapterPos: Int)
    }

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
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_picture_list, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return getItems().size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItems(): List<PictureEntity> {
        return asyncDiffer.currentList
    }

    fun getItem(position: Int): PictureEntity {
        return getItems()[position]
    }

    fun submitItems(items: List<PictureEntity>) {
        asyncDiffer.submitList(items)
    }


    class ItemViewHolder(val binding: ItemPictureListBinding, private val listener: PictureClickListener) : RecyclerView.ViewHolder(binding.root) {

        private val set = ConstraintSet()

        init {
            binding.root.setOnClickListener { view -> listener.onItemClick(view, adapterPosition)}
        }

        fun bind(picture : PictureEntity) {
            binding.pictureEntity = picture
            binding.executePendingBindings()

            val ratio = String.format("%d:%d", picture.width, picture.height)
            set.clone(binding.constraintLayout)
            set.setDimensionRatio(binding.picture.id, ratio)
            set.applyTo(binding.constraintLayout)
        }

    }
}