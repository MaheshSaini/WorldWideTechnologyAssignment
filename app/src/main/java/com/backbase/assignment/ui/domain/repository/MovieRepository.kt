package com.backbase.assignment.ui.domain.repository

import com.backbase.assignment.ui.data.model.movie.Movie
import com.backbase.assignment.ui.data.model.movie.MovieDetails

interface MovieRepository {

    suspend fun getMovies(): List<Movie>?
    suspend fun updateMovies(): List<Movie>?
    suspend fun getMoviesFromPlaying(): List<Movie>?
    suspend fun getDataFromMovieId(movieId: String): MovieDetails

}