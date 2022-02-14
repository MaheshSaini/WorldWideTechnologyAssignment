package com.backbase.assignment.ui.data.model

import com.backbase.assignment.ui.data.model.movie.Movie
import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("results")
    val movies: List<Movie>

)