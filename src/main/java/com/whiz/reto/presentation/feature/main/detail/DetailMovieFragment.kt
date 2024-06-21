package com.whiz.reto.presentation.feature.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.whiz.reto.R
import com.whiz.core.base.BaseFragment
import com.whiz.core.extensions.updateImageDrawable
import com.whiz.core.util.isConnected
import com.whiz.reto.databinding.FragmentDetailMovieBinding
import com.whiz.reto.domain.entity.movies.DetailMovie
import com.whiz.reto.presentation.feature.main.listmovies.ListMoviesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : com.whiz.core.base.BaseFragment() {

    private val viewModel: DetailMovieViewModel by viewModels()
    private lateinit var binding: FragmentDetailMovieBinding

    private var movieId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            movieId = this.getInt(ListMoviesFragment.ID_MOVIE, 0)
            println()
        }
        viewModel.getMovieDetail(movieId, requireContext().isConnected())

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

    private fun setupView(detailMovie: DetailMovie?) {
        detailMovie?.let {
            if (it.id == 0) {
                showMessageException("No tienes conexion a internet, ni data local de este pokemon")
            } else {
                binding.tvName.text = it.name
                binding.customImageView.setImage(it.sprites?.backDefault, it.name)
                binding.tvHeightValue.text = it.height.toString()
                binding.tvWeightValue.text = it.weight.toString()

                binding.tvTypesValue.text = it.types.joinToString(separator = "\n") { it.name }

                updateImageFavorite(detailMovie.isFavorite)

                binding.ivFavorite.setOnClickListener {
                    detailMovie.isFavorite = !detailMovie.isFavorite
                    updateImageFavorite(detailMovie.isFavorite)
                }

            }
        }
    }

    private fun updateImageFavorite(isFavorite: Boolean) {
        val image = if (isFavorite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite_border
        binding.ivFavorite.updateImageDrawable(image)
    }
}
