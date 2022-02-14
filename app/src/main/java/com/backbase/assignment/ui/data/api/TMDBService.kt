package com.backbase.assignment.ui.data.api

import com.backbase.assignment.ui.data.model.MovieList
import com.backbase.assignment.ui.data.model.albums.AlbumsResponse
import com.backbase.assignment.ui.data.model.albums.PhotosResponse
import com.backbase.assignment.ui.data.model.movie.MovieDetails
import com.backbase.assignment.ui.network.response.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(
            "api_key"
        ) apiKey: String
    ): Response<MovieList>

    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("page") page: String
    ): Response<MovieList>

    @GET("movie/{MOVIE_ID}")
    suspend fun getDataFromMovieID(
        @Path("MOVIE_ID") movieId: String,
        @Query("api_key") apiKey: String
    ): Response<MovieDetails>

    @GET("movie/popular")
    suspend fun getPopularMoviesWithPaging(
        @Query("api_key") apiKey: String?,
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("albums")
    suspend fun getAlbumsData(): Response<AlbumsResponse>
    @GET("photos")
    suspend fun getPhotoData(): Response<PhotosResponse>
}