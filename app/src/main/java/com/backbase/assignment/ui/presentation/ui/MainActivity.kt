package com.backbase.assignment.ui.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.backbase.assignment.R
import com.backbase.assignment.databinding.ActivityMainBinding
import com.backbase.assignment.ui.data.model.MovieList
import com.backbase.assignment.ui.data.model.movie.Movie
import com.backbase.assignment.ui.domain.adapter.ItemClickListener
import com.backbase.assignment.ui.domain.adapter.MovieLoadStateAdapter
import com.backbase.assignment.ui.domain.adapter.MoviesAdapter
import com.backbase.assignment.ui.domain.adapter.MoviesBannerAdapter
import com.backbase.assignment.ui.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class MainActivity : AppCompatActivity(), ItemClickListener {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var movieAdapter: MoviesAdapter
    private lateinit var mainBinding: ActivityMainBinding
    private val movieList: ArrayList<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        movieAdapter = MoviesAdapter(this)
        mainBinding.movieRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter { movieAdapter.retry() }
            )
        }
        displayMoviesNowPlaying()
        lifecycleScope.launch {
            mainBinding.movieBannerProgressBar.visibility = View.VISIBLE
            val getBannerData: MovieList = viewModel.repository.getMoviesPlayingNow()
            if (getBannerData.movies.isNotEmpty()) {
                movieList.addAll(getBannerData.movies)
                setAdapterForPlayingNow(movieList)
            }
        }

        lifecycleScope.launch {
            viewModel.movies.collectLatest {
                movieAdapter.submitData(it)
            }
        }
        mainBinding.btnRetry.setOnClickListener {
            movieAdapter.retry()
        }
        // show the loading state for te first load
        movieAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {

                mainBinding.btnRetry.visibility = View.GONE

                // Show ProgressBar
                mainBinding.progressBar.visibility = View.VISIBLE
            } else {
                // Hide ProgressBar
                mainBinding.progressBar.visibility = View.GONE

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        mainBinding.btnRetry.visibility = View.VISIBLE
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun displayMoviesNowPlaying() {
        mainBinding.bannerRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mainBinding.bannerRecyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        mainBinding.bannerRecyclerView.addItemDecoration(dividerItemDecoration)
        val resId = R.anim.layout_animation_fall_down
        val animation = AnimationUtils.loadLayoutAnimation(this, resId)
        mainBinding.bannerRecyclerView.setLayoutAnimation(animation)
        mainBinding.bannerRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
    }

    private fun setAdapterForPlayingNow(movieList: ArrayList<Movie>) {
        if (movieList.size > 0) {
            val adapter = MoviesBannerAdapter(this, movieList)
            mainBinding.bannerRecyclerView.adapter = adapter
            adapter.setClickListener(this)
            adapter.notifyDataSetChanged()
            mainBinding.movieBannerProgressBar.visibility = View.GONE
        }
    }

    override fun onClick(view: View?, position: Int) {
        if (movieList.size > 0) {
            val intent = Intent(this, MovieBannerDetailsActivity::class.java)
            intent.putExtra("MOVIE_ID", movieList.get(position).id)
            startActivity(intent)
        }
    }

}