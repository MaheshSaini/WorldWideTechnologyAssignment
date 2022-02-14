package com.backbase.assignment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.backbase.assignment.ui.data.model.MovieBannerDetails
import com.backbase.assignment.ui.domain.repository.Repository
import com.backbase.assignment.ui.domain.repository.paged.MoviePagingSource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainViewModel(val repository: Repository) : ViewModel() {

    val movies: Flow<PagingData<MovieModel>> = getMovieListStream()
        .map { pagingData -> pagingData.map { MovieModel.MovieItem(it) } }
        .map {
            it.insertSeparators<MovieModel.MovieItem, MovieModel> { before, after ->
                if (after == null) {
                    // we're at the end of the list
                    return@insertSeparators MovieModel.SeparatorItem("End of list")
                }

                if (before == null) {
                    // we're at the beginning of the list
                    return@insertSeparators null//MovieModel.SeparatorItem("${after.roundedVoteCount}0.000+ stars")
                }
                // check between 2 items
                if (before.roundedVoteCount > after.roundedVoteCount) {
                    if (after.roundedVoteCount >= 1) {
                        MovieModel.SeparatorItem("Less than ${before.roundedVoteCount}0000 vote count")
                    } else {
                        MovieModel.SeparatorItem("Less than 10000 vote count")
                    }
                } else {
                    // no separator
                    null
                }
            }
        }


    private fun getMovieListStream(): Flow<PagingData<MovieBannerDetails>> {
        return Pager(PagingConfig(20)) {
            MoviePagingSource(repository)
        }.flow
    }
}

sealed class MovieModel {
    data class MovieItem(val movieBanner: MovieBannerDetails) : MovieModel()
    data class SeparatorItem(val description: String) : MovieModel()
}

val MovieModel.MovieItem.roundedVoteCount: Int
    get() = this.movieBanner.vote_count / 10000