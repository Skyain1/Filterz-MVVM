package com.example.filterz.listeners

import com.example.filterz.data.ImageFilter

interface ImageFilterListener {
    fun onFilterSelected(imageFilter: ImageFilter)
}