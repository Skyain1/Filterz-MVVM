package com.example.filterz.Activities.savedimages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import com.example.filterz.Activities.editimage.EditImageActivity
import com.example.filterz.Activities.filteredImage.FilteredImageActivity
import com.example.filterz.Adapters.SavedImagesAdapter
import com.example.filterz.R
import com.example.filterz.Utilities.displayToast
import com.example.filterz.Viewmodels.SavedImagesViewModel
import com.example.filterz.databinding.ActivitySavedImagesBinding
import com.example.filterz.listeners.SavedImageListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class SavedImagesActivity : AppCompatActivity(), SavedImageListener{
    private lateinit var binding: ActivitySavedImagesBinding
    private val viewModel: SavedImagesViewModel by  viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
        setListeners()
        viewModel.loadSavedImages()
    }
    private fun setupObserver(){
        viewModel.savedImagesUiState.observe(this, {
            val savedImagesDataState = it?: return@observe
            binding.progress.visibility=
                if(savedImagesDataState.isLoading) View.VISIBLE else View.GONE

            savedImagesDataState.savedImages?.let { savedImages ->
                SavedImagesAdapter(savedImages,this).also { adapter ->
                    with(binding.rec){
                        this.adapter = adapter
                        visibility = View.VISIBLE
                    }
                }
            } ?: run {
                savedImagesDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
    }

    private fun setListeners(){
        binding.back.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onImageClicked(file: File) {
        val fileUri = FileProvider.getUriForFile(
            applicationContext,
            "${packageName}.provider",
            file
        )
        Intent(
            applicationContext,
            FilteredImageActivity::class.java
        ).also { filteredImageIntent ->
            filteredImageIntent.putExtra(EditImageActivity.KEY_FILTERED_IMAGE_URI,fileUri)
            startActivity(filteredImageIntent)
        }
    }
}