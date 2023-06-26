package com.example.filterz.dependencyinjection

import com.example.filterz.Viewmodels.EditImageViewModel
import com.example.filterz.Viewmodels.SavedImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

    val viewModelModule = module {
    viewModel { EditImageViewModel(editImageRepository = get  ()) }
        viewModel { SavedImagesViewModel(savedImagesRepository = get()) }
}