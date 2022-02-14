package com.backbase.assignment.ui.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.backbase.assignment.R
import com.backbase.assignment.databinding.MoviesDetailsBinding
import com.backbase.assignment.ui.data.model.movie.MovieDetails
import com.backbase.assignment.ui.viewmodel.MovieBannerDetailsViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieBannerDetailsActivity : AppCompatActivity() {
    private val viewModel: MovieBannerDetailsViewModel by viewModel()
    private lateinit var mainBinding: MoviesDetailsBinding
    private var movieId: Int = 0
    private var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = MoviesDetailsBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        updateToolBarTitle("")
        if (intent.extras != null) {
            val bundle = intent.extras
            movieId = bundle!!.getInt("MOVIE_ID")
            getBannerDetails(movieId)
        }


    }

    private fun getBannerDetails(movieId: Int) {
        lifecycleScope.launch {
            mainBinding.movieProgressBar.visibility = View.VISIBLE
            val movieDetails: MovieDetails =
                viewModel.repository.fetchPopularMoviesDetails("" + movieId)
            displayPopularMovies(movieDetails)
        }
    }

    private fun displayPopularMovies(movieDetails: MovieDetails) {
        val posterURL =
            "https://image.tmdb.org/t/p/w500" + movieDetails.posterPath//https://image.tmdb.org/t/p/w500/iB8rf8FYHGlrbmLybCo6Mpg8hNt.jpg
        Glide.with(mainBinding.poster.context)
            .load(posterURL)
            .into(mainBinding.poster)
        mainBinding.title.text = movieDetails.title
        mainBinding.descriptionTxt.text = movieDetails.overview
        mainBinding.dateTimeTxt.text = movieDetails.releaseDate
        mainBinding.movieProgressBar.visibility = View.GONE
    }

    private fun updateToolBarTitle(title: String) {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar?.let {
            it.title = title
        }
        supportActionBar?.title = title
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            toolbar?.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
            toolbar?.setNavigationOnClickListener {
                finish()
            }
        }


    }
}