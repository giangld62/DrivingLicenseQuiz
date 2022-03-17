package com.giangle.project.ui.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giangle.project.databinding.ItemPhotoBinding
import com.giangle.project.model.Photo

class ViewPager2Adapter : RecyclerView.Adapter<ViewPager2Adapter.ViewHolder>() {

    private val photos = arrayListOf<Photo>()

    fun submitList(temps: List<Photo>) {
        photos.clear()
        photos.addAll(temps)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount() = photos.size

    class ViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(img: Photo) {
            binding.data = img
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )
}
