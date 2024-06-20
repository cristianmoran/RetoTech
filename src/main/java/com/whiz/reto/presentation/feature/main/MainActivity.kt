package com.whiz.reto.presentation.feature.main

import android.os.Bundle
import androidx.activity.viewModels
import com.whiz.reto.core.BaseActivity
import com.whiz.reto.databinding.ActivityMainBinding
import com.whiz.reto.presentation.feature.detail.DetailMovieActivity
import com.whiz.reto.presentation.feature.main.adapter.MovieAdapter
import com.whiz.reto.core.util.isConnected
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var adapter = MovieAdapter(MovieAdapter.MovieClickListener(
        onClick = { movie ->
            val intent = DetailMovieActivity.newInstance(this@MainActivity, movie.id)
            startActivity(intent)
        },
        onSeeMore = {
            viewModel.loadMoreMovies(this.isConnected())
        }
    ))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRvMovies()
        initObserver()

        viewModel.loadMoreMovies(this.isConnected())
    }

    override fun initObserver() {
        viewModel.moviesLiveData.observe(this) {
            adapter.submitList(it)
        }

        viewModel.loadingStateLivaData.observe(this) {
            handleVisibilityProgressLoadStates(it)
        }

        viewModel.mutableException.observe(this) {
            validateException(it)
        }

    }

    private fun initRvMovies() {
        binding.rvMovies.adapter = adapter

    }
}