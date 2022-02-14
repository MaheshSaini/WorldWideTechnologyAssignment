package com.backbase.assignment.ui.network

import com.backbase.assignment.BuildConfig
import com.backbase.assignment.ui.data.api.TMDBService
import com.backbase.assignment.ui.data.model.MovieList
import com.backbase.assignment.ui.data.model.MovieResult
import com.backbase.assignment.ui.data.model.albums.AlbumsResponse
import com.backbase.assignment.ui.data.model.albums.PhotosResponse
import com.backbase.assignment.ui.data.model.movie.Movie
import com.backbase.assignment.ui.data.model.movie.MovieDetails
import com.backbase.assignment.ui.network.response.MovieListResponse


class MovieAppService(private val api: TMDBService) : BaseService() {

    suspend fun fetchPopularMovies(page: Int): MovieResult<MovieListResponse> {
        return createCall {
            api.getPopularMoviesWithPaging(
                BuildConfig.API_KEY,
                page
            )
        }
    }

    suspend fun fetchPopularMoviesBanner(): MovieResult<MovieList> {
        return createCall {
            api.getMoviesNowPlaying(
                BuildConfig.API_KEY,
                "undefined"
            )
        }
    }

    suspend fun fetchPopularMoviesDetails(movieID: String): MovieResult<MovieDetails> {
        return createCall {
            api.getDataFromMovieID(
                movieID,
                BuildConfig.API_KEY
            )
        }
    }

    suspend fun getAlbumsData(): MovieResult<AlbumsResponse> {
        return createCall {
            api.getAlbumsData()
        }
    }

    suspend fun getPhotoData(): MovieResult<PhotosResponse> {
        return createCall {
            api.getPhotoData()
        }
    }
}