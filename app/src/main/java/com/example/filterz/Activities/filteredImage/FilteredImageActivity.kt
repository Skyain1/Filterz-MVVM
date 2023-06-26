package com.example.filterz.Activities.filteredImage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.filterz.Activities.editimage.EditImageActivity
import com.example.filterz.R
import com.example.filterz.databinding.ActivityFilteredImageBinding

class FilteredImageActivity : AppCompatActivity() {

    private lateinit var fileuri: Uri
    private lateinit var binding:ActivityFilteredImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilteredImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayFilteredImage()
        setlisteners()
    }
    private fun displayFilteredImage(){
        intent.getParcelableExtra<Uri>(EditImageActivity.KEY_FILTERED_IMAGE_URI )?.let { imageUri ->
            fileuri = imageUri
            binding.image.setImageURI(imageUri)
        }
    }
    private fun setlisteners(){
        binding.share.setOnClickListener{
            with(Intent(Intent.ACTION_SEND)){
                putExtra(Intent.EXTRA_STREAM, fileuri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                type = "image/*"
                startActivity(this)
            }
        }
        val iconColor = ContextCompat.getColorStateList(this, R.color.icon_color)
        binding.back.imageTintList = iconColor
        binding.back.setOnClickListener{
            onBackPressed()
        }
    }
}