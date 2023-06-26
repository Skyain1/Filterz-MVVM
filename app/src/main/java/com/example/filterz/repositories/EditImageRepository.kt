package com.example.filterz.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.example.filterz.data.ImageFilter

interface EditImageRepository {
    suspend fun prepareImagePreview(imageUri: Uri): Bitmap?
    suspend fun getImageFilters(image: Bitmap):List<ImageFilter>
    suspend fun saveFilredImage(filtered: Bitmap): Uri?
}