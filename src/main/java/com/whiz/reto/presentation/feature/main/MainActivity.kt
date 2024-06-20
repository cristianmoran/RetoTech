package com.whiz.reto.presentation.feature.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.whiz.reto.databinding.ActivityMainBinding
import com.whiz.reto.presentation.feature.main.adapter.MovieAdapter
import com.whiz.reto.util.isConnected
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var adapter = MovieAdapter(MovieAdapter.MovieClickListener(
        onClick = { _ ->
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

    private fun initObserver() {
        viewModel.moviesLiveData.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun initRvMovies() {
        binding.rvMovies.adapter = adapter

    }
}