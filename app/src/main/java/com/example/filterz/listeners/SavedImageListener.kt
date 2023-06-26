package com.example.filterz.listeners

import java.io.File

interface SavedImageListener {
    fun onImageClicked(file: File)
}