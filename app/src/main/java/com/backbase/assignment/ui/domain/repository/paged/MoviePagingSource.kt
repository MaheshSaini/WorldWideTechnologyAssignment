package com.backbase.assignment.ui.domain.repository.paged

import androidx.paging.PagingSource
import com.backbase.assignment.ui.data.model.MovieBannerDetails
import com.backbase.assignment.ui.domain.repository.Repository

class MoviePagingSource(
    private val repository: Repository
) : PagingSource<Int, MovieBannerDetails>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieBannerDetails> {

        return try {
            val nextPage = params.key ?: 1
            val movieListResponse = repository.getPopularMovies(nextPage)

            LoadResult.Page(
                data = movieListResponse.results!!,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < movieListResponse.totalPages!!)
                    movieListResponse.page?.plus(1) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}
