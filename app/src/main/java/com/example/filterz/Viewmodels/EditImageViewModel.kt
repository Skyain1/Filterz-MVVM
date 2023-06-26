package com.example.filterz.Viewmodels

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filterz.Utilities.Coroutines
import com.example.filterz.data.ImageFilter
import com.example.filterz.repositories.EditImageRepository

class EditImageViewModel(private val editImageRepository: EditImageRepository): ViewModel() {

    //region:: Prepare image preview


    private val imagePreviewDataState = MutableLiveData<ImagePreviewDataState>()
    val imagePreviewUiState: LiveData<ImagePreviewDataState> get() = imagePreviewDataState

    fun prepareImagePreview(imageUri: Uri) {
        Coroutines.io {
            kotlin.runCatching {
                emitImagePreviewUiState(isLoading = true)
                editImageRepository.prepareImagePreview(imageUri)
            }.onSuccess { bitmap ->
                if(bitmap!=null){
                    emitImagePreviewUiState(bitmap = bitmap)
                }else{
                    emitImagePreviewUiState(error = " Unable to prepare image preview")
                }
            }.onFailure {
                emitImagePreviewUiState(error = it.message.toString())
            }
        }
    }

    private fun emitImagePreviewUiState(
        isLoading: Boolean = false,
        bitmap: Bitmap?= null,
        error: String? = null
    ){
        val dataState = ImagePreviewDataState(isLoading,bitmap,error)
        imagePreviewDataState.postValue(dataState)
    }

    data class ImagePreviewDataState(
        val isLoading: Boolean,
        val bitmap: Bitmap?,
        val error: String?
    )
    //endregion

    //region :: Load Image Filters
    private val imageFiltersDataState = MutableLiveData<ImageFiltersDataState>()
    val imageFiltersUiState: LiveData<ImageFiltersDataState> get() = imageFiltersDataState

    fun loadImageFilters(originalImage: Bitmap) {
        Coroutines.io {
            runCatching {
                emitImageFiltersUiState(isLoading = true)
                editImageRepository.getImageFilters(getPreviewImage(originalImage))
            }.onSuccess { imageFilters->
                emitImageFiltersUiState(imageFilters =imageFilters)
            }.onFailure {
                emitImageFiltersUiState(error = it.message)
            }
        }
    }
    private fun getPreviewImage(originalImage:Bitmap):Bitmap{
        return runCatching {
            val previewWidtn = 150
            val previewHeight = originalImage.height * previewWidtn / originalImage.width
            Bitmap.createScaledBitmap(originalImage, previewWidtn, previewHeight, false)
        }.getOrDefault(originalImage)
    }


    private fun emitImageFiltersUiState(
        isLoading: Boolean = false,
        imageFilters : List<ImageFilter>? = null,
        error: String? = null
    ){
        val dataState = ImageFiltersDataState(isLoading, imageFilters , error)
        imageFiltersDataState.postValue(dataState)
    }

    data class ImageFiltersDataState(
        val isLoading: Boolean,
        val ImageFilters: List<ImageFilter>?,
        val error: String?
    )
    //endregion


    //region:: Save Filtered image

    private val saveFilteredImageDataState = MutableLiveData<SaveFilteredImageDataState>()
    val saveFilteredImageUiState:LiveData<SaveFilteredImageDataState> get() = saveFilteredImageDataState

    fun saveFilteredImage(filteredBitmap: Bitmap){
        Coroutines.io {
            runCatching {
                emitSaveFilteredImageUiState(isLoading = true)
                editImageRepository.saveFilredImage(filteredBitmap)
            }.onSuccess { savedimage ->
                emitSaveFilteredImageUiState(uri = savedimage)
            }.onFailure {
                emitSaveFilteredImageUiState(error = it.message.toString())
            }
        }
    }

    private fun emitSaveFilteredImageUiState(
        isLoading: Boolean = false,
        uri: Uri? = null,
        error: String? = null
    ){
        val dataState = SaveFilteredImageDataState(isLoading,uri,error)
        saveFilteredImageDataState.postValue(dataState)
    }

    data class SaveFilteredImageDataState(
        val isLoading: Boolean,
        val uri: Uri?,
        val error: String?
    )

    //endregion
}