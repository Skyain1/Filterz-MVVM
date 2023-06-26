package com.example.filterz.Adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filterz.databinding.SavedImageViewholderBinding
import com.example.filterz.listeners.SavedImageListener
import java.io.File


class SavedImagesAdapter (private val savedImages: List<Pair<File,Bitmap>>,
private val savedImageListener: SavedImageListener):
    RecyclerView.Adapter<SavedImagesAdapter.SavedImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedImageViewHolder {
        val binding = SavedImageViewholderBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return SavedImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedImageViewHolder, position: Int) {
      with(holder){
          with(savedImages[position]){
              binding.imageSaved.setImageBitmap(second)
              binding.imageSaved.setOnClickListener{
                  savedImageListener.onImageClicked(first)
              }
          }
      }
    }

    override fun getItemCount()= savedImages.size

    inner class SavedImageViewHolder(val binding: SavedImageViewholderBinding):
            RecyclerView.ViewHolder(binding.root)
}