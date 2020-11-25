package com.example.gallery.di

import com.example.gallery.browser.BrowserAdapter
import com.example.gallery.browser.BrowserViewModel
import com.example.gallery.preview.PreviewViewModel
import com.example.gallery.repository.ImageRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    single { ImageRepository(get()) }
    factory { BrowserAdapter() }
    viewModel { BrowserViewModel(get()) }
    viewModel { PreviewViewModel(get()) }
}