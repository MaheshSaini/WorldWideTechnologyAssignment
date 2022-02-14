package com.backbase.assignment.ui.presentation.di

import com.backbase.assignment.ui.domain.repository.Repository
import com.backbase.assignment.ui.network.MovieAppService
import org.koin.dsl.module

val repositoryModule = module {
    single { createRepository(get()) }
}

fun createRepository(
    movieAppService: MovieAppService
) : Repository = Repository(movieAppService)