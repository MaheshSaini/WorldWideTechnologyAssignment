package com.backbase.assignment.ui.domain.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.databinding.MovieItemSeperatorBinding
import com.backbase.assignment.databinding.MoviesListItemBinding
import com.backbase.assignment.ui.viewmodel.MovieModel
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class MoviesAdapter(context: Context) : PagingDataAdapter<MovieModel, RecyclerView.ViewHolder>(
    MovieModelComparator
) {
    var mContext: Context

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val movieModel: MovieModel = getItem(position)!!

        movieModel.let {
            when (movieModel) {
                is MovieModel.MovieItem -> {
                    val viewHolder = holder as MovieViewHolder
                    viewHolder.movieItemBinding.txtMovieTitle.text =
                        movieModel.movieBanner.original_title
                    val releaseDate = movieModel.movieBanner.release_date

                    val formatedDate = SimpleDateFormat("yyyy-MM-dd")
                    val getDateFormat: Date = formatedDate.parse(releaseDate)
                    viewHolder.movieItemBinding.txtDateTime.text =
                        "" + getDateFormat
                    val rating: Double = (movieModel.movieBanner.vote_average) * 10
                    val ratingFinal = rating.toInt()
                    viewHolder.movieItemBinding.txtProgressRating.text =
                        "$ratingFinal%"
                    if (ratingFinal < 50) {
                        viewHolder.movieItemBinding.pbRating.setProgressDrawable(
                            getDrawable(
                                mContext,
                                R.drawable.progressbar_states_yellow
                            )
                        )
                    } else {
                        viewHolder.movieItemBinding.pbRating.setProgressDrawable(
                            getDrawable(
                                mContext,
                                R.drawable.progressbar_states_green
                            )
                        )
                    }

                    val posterURL =
                        "https://image.tmdb.org/t/p/w500" + movieModel.movieBanner.poster_path//https://image.tmdb.org/t/p/w500/iB8rf8FYHGlrbmLybCo6Mpg8hNt.jpg
                    Glide.with(mContext)
                        .load(posterURL)
                        .into(viewHolder.movieItemBinding.ivBanner)
                }
                is MovieModel.SeparatorItem -> {
                    /* val viewHolder = holder as MovieSeparatorViewHolder
                     viewHolder.movieItemSeperatorBinding
                         .separatorDescription.text = movieModel.description*/
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MovieModel.MovieItem -> R.layout.movies_list_item
            is MovieModel.SeparatorItem -> R.layout.movie_item_seperator
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.movies_list_item -> {
                MovieViewHolder(
                    MoviesListItemBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {
                MovieSeparatorViewHolder(
                    MovieItemSeperatorBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

    class MovieViewHolder(val movieItemBinding: MoviesListItemBinding) :
        RecyclerView.ViewHolder(movieItemBinding.root)

    class MovieSeparatorViewHolder(val movieItemSeperatorBinding: MovieItemSeperatorBinding) :
        RecyclerView.ViewHolder(movieItemSeperatorBinding.root)

    companion object {
        private val MovieModelComparator = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return (oldItem is MovieModel.MovieItem && newItem is MovieModel.MovieItem &&
                        oldItem.movieBanner.id == newItem.movieBanner.id) ||
                        (oldItem is MovieModel.SeparatorItem && newItem is MovieModel.SeparatorItem &&
                                oldItem.description == newItem.description)
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
                oldItem == newItem
        }
    }

    init {
        mContext = context
    }
}