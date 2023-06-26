package com.example.filterz.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.filterz.R
import com.example.filterz.data.ImageFilter
import com.example.filterz.databinding.FilterViewholderBinding
import com.example.filterz.listeners.ImageFilterListener

class ImageFiltersAdapter(private  val imageFilters: List<ImageFilter>, private val imageFilterListener: ImageFilterListener):
RecyclerView.Adapter<ImageFiltersAdapter.ImageFilterViewHolder>(){

    private var selectedFilterPosition = 0
    private var previousSelectedPosition= 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFilterViewHolder {
        val binding = FilterViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageFilterViewHolder, position: Int) {
        with(holder){
            with(imageFilters[position]){
                binding.preview.setImageBitmap(filterPreview)
                binding.filtername.text =name
                binding.root.setOnClickListener{
                    if(position!=selectedFilterPosition)
                    {
                        imageFilterListener.onFilterSelected(this)
                        previousSelectedPosition = selectedFilterPosition
                        selectedFilterPosition = position
                        with(this@ImageFiltersAdapter){
                            notifyItemChanged(previousSelectedPosition, Unit)
                            notifyItemChanged(selectedFilterPosition, Unit)
                        }
                    }

                }
            }
            binding.filtername.setTextColor(ContextCompat.getColor(
                binding.filtername.context,
                if(selectedFilterPosition == position){
                    R.color.yellow
                }else {
                    R.color.whitetext
                }
            ))
        }
    }

    override fun getItemCount() = imageFilters.size

    inner class ImageFilterViewHolder(val binding: FilterViewholderBinding):
            RecyclerView.ViewHolder(binding.root)
}