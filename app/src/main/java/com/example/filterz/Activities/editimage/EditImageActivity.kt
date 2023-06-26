package com.example.filterz.Activities.editimage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.filterz.Activities.filteredImage.FilteredImageActivity
import com.example.filterz.Activities.main.MainActivity
import com.example.filterz.Adapters.ImageFiltersAdapter
import com.example.filterz.Utilities.displayToast
import com.example.filterz.Utilities.show
import com.example.filterz.Viewmodels.EditImageViewModel
import com.example.filterz.data.ImageFilter
import com.example.filterz.databinding.ActivityEditImageBinding
import com.example.filterz.listeners.ImageFilterListener
import jp.co.cyberagent.android.gpuimage.GPUImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageActivity : AppCompatActivity(),ImageFilterListener{

    companion object{
        const val KEY_FILTERED_IMAGE_URI = "filteredImageUri"
    }

    private lateinit var binding:ActivityEditImageBinding
    private val viewmodel:EditImageViewModel by viewModel ()

    private lateinit var gpuImage:GPUImage

    //Image bitmaps
    private lateinit var originalBitmap: Bitmap
    private val filteredBitmap = MutableLiveData<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setlisteners()
        setupObservers()
        prepareImagePreview()
    }

    private fun setupObservers(){
        viewmodel.imagePreviewUiState.observe(this,{
            val dataState = it ?: return@observe
            binding.progress.visibility =
                if(dataState.isLoading) View.VISIBLE else View.GONE
            dataState.bitmap?.let { bitmap ->
                //For the first time - original
                originalBitmap = bitmap
                filteredBitmap.value = bitmap

                with(originalBitmap){
                    gpuImage.setImage(this)
                    binding.preview.show()
                    viewmodel.loadImageFilters(this)
                }

            }?: kotlin.run {
                dataState.error?.let { error->
                   displayToast(error)
                }
            }
        })

        viewmodel.imageFiltersUiState.observe(this,{
            val imageFiltersDataState = it?:return@observe
            binding.imageprogress.visibility=
                if(imageFiltersDataState.isLoading)View.VISIBLE else View.GONE
            imageFiltersDataState.ImageFilters?.let { imageFilters ->
                ImageFiltersAdapter(imageFilters,this).also { adapter ->
                    binding.rec.adapter = adapter
                }
            }?: kotlin.run {
                imageFiltersDataState.error?.let { error->
                    displayToast(error)
                }
            }
        })

        filteredBitmap.observe(this,{bitmap ->
            binding.preview.setImageBitmap(bitmap)

        })

        viewmodel.saveFilteredImageUiState.observe(this,{

            val saveFilteredImageDataState = it ?: return@observe
            if(saveFilteredImageDataState.isLoading){
                binding.accept.visibility = View.GONE
                binding.saving.visibility = View.VISIBLE
            }else{
                binding.saving.visibility = View.GONE
                binding.accept.visibility = View.VISIBLE
            }
            saveFilteredImageDataState.uri?.let { saveImageUri ->
                Intent(
                    applicationContext,FilteredImageActivity::class.java
                ).also{filteredintent ->
                    filteredintent.putExtra(KEY_FILTERED_IMAGE_URI, saveImageUri)
                    startActivity(filteredintent)
                }
            }?: kotlin.run{
                saveFilteredImageDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
    }
    private fun prepareImagePreview(){
        gpuImage = GPUImage(applicationContext)
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageuri->
            viewmodel.prepareImagePreview(imageuri)
        }
    }
    private fun displayImagePreview(){
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageuri->
            val inputStream = contentResolver.openInputStream(imageuri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.preview.setImageBitmap(bitmap)
            binding.preview.visibility= View.VISIBLE
        }
    }
    private fun setlisteners(){
        binding.back.setOnClickListener{
            onBackPressed()
        }
        binding.accept.setOnClickListener{
            filteredBitmap.value?.let {
                viewmodel.saveFilteredImage(it)
            }
        }
        /*
        * this wall - original image
        * */
        binding.preview.setOnLongClickListener{
                binding.preview.setImageBitmap(originalBitmap)
            return@setOnLongClickListener false
        }
        binding.preview.setOnClickListener{
            binding.preview.setImageBitmap(filteredBitmap.value)
        }
    }

    override fun onFilterSelected(imageFilter: ImageFilter) {
        with(imageFilter){
            with(gpuImage){
                setFilter(filter)
                filteredBitmap.value= bitmapWithFilterApplied
            }
        }
    }
}