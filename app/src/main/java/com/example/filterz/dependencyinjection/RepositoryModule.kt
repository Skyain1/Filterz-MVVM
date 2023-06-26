package com.example.filterz.dependencyinjection

import com.example.filterz.repositories.EditImageRepository
import com.example.filterz.repositories.EditImageRepositoryImpl
import com.example.filterz.repositories.SavedImagesRepository
import com.example.filterz.repositories.SavedImagesRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryNodule = module {
    factory<EditImageRepository> {EditImageRepositoryImpl(androidContext())}
    factory <SavedImagesRepository>{SavedImagesRepositoryImpl(androidContext())}
}