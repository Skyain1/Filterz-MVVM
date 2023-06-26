package com.example.filterz.Activities.main

import RoundedImageAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.viewpager2.widget.ViewPager2
import com.example.filterz.Activities.editimage.EditImageActivity
import com.example.filterz.Activities.savedimages.SavedImagesActivity
import com.example.filterz.R
import com.example.filterz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object{
        private const val REQUEST_CODE_PICK_IMAGE=1
        const val KEY_IMAGE_URI="imageuri"
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        setViewPager()
    }

    private fun setListeners() {
        binding.edit.setOnClickListener{
            Intent(
            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also {
                pickerIntent->pickerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(pickerIntent, REQUEST_CODE_PICK_IMAGE)
            }
        }
        binding.saved.setOnClickListener{
            Intent(applicationContext, SavedImagesActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== REQUEST_CODE_PICK_IMAGE && resultCode== RESULT_OK){
            data?.data?.let { imageuri->
                Intent(applicationContext, EditImageActivity::class.java).also { editintent->
                    editintent.putExtra(KEY_IMAGE_URI,imageuri)
                    startActivity(editintent)
                }
            }
        }
    }
    private fun setViewPager(){
        val imageUrls: List<Int> = listOf(
           R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3
        , R.drawable.fadfaf
        )
        val adapter = RoundedImageAdapter(this, imageUrls)
            binding.viewPager.adapter = adapter


        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {}

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                when (position) {
                    0 -> {
                        binding.dot1.setImageResource(R.drawable.active_dot)
                        binding.dot2.setImageResource(R.drawable.unactive_dot)
                        binding.dot3.setImageResource(R.drawable.unactive_dot)
                        binding.dot4.setImageResource(R.drawable.unactive_dot)
                    }
                    1 -> {
                        binding.dot1.setImageResource(R.drawable.unactive_dot)
                        binding.dot2.setImageResource(R.drawable.active_dot)
                        binding.dot3.setImageResource(R.drawable.unactive_dot)
                        binding.dot4.setImageResource(R.drawable.unactive_dot)
                    }
                    2 -> {
                        binding.dot1.setImageResource(R.drawable.unactive_dot)
                        binding.dot2.setImageResource(R.drawable.unactive_dot)
                        binding.dot3.setImageResource(R.drawable.active_dot)
                        binding.dot4.setImageResource(R.drawable.unactive_dot)
                    }
                    3 -> {
                        binding.dot1.setImageResource(R.drawable.unactive_dot)
                        binding.dot2.setImageResource(R.drawable.unactive_dot)
                        binding.dot3.setImageResource(R.drawable.unactive_dot)
                        binding.dot4.setImageResource(R.drawable.active_dot)
                    }
                }
            }
        })
    }
}