package com.whiz.reto.presentation.feature.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.whiz.reto.core.BaseActivity
import com.whiz.reto.databinding.ActivityDetailMovieBinding
import com.whiz.reto.domain.entity.movies.DetailMovie
import com.whiz.reto.util.isConnected
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : BaseActivity() {

    private val viewModel: DetailMovieViewModel by viewModels()
    private lateinit var binding: ActivityDetailMovieBinding

    private var movieId = 0

    companion object {
        fun newInstance(context: Context, movieId: Int): Intent {
            return Intent(context, DetailMovieActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }

        private const val TAG = "DetailMovieActivity"
        private const val MOVIE_ID = "MOVIE_ID"
    }

    private fun getArguments() {
        movieId = intent.getIntExtra(MOVIE_ID, 0)
    }


    override fun initObserver() {

        viewModel.movieDetailLiveData.observe(this) {
            setupView(it)
        }

        viewModel.loadingStateLivaData.observe(this) {
            handleVisibilityProgressLoadStates(it)
        }

        viewModel.mutableException.observe(this) {
            validateException(it)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getArguments()
        viewModel.getMovieDetail(movieId, this.isConnected())
    }

    private fun setupView(detailMovie: DetailMovie?) {
        detailMovie?.let {
            if (it.id == 0) {
                showMessageSnack("No tienes conexion a internet")
            } else {
                binding.tvName.text = it.name

            }
        }
    }
}