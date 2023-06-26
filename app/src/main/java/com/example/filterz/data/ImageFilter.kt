package com.example.filterz.data

import android.graphics.Bitmap
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter

data class ImageFilter (
     val name: String,
     val filter: GPUImageFilter,
     val filterPreview:Bitmap
         )