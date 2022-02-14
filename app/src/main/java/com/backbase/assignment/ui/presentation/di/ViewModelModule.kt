package com.backbase.assignment.ui.presentation.di

import com.backbase.assignment.ui.presentation.ui.album.AlbumViewModel
import com.backbase.assignment.ui.viewmodel.MainViewModel
import com.backbase.assignment.ui.viewmodel.MovieBannerDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { MovieBannerDetailsViewModel(get()) }
    viewModel { AlbumViewModel(get()) }
}