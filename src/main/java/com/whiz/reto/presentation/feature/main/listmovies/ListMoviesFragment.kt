package com.whiz.reto.presentation.feature.main.listmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.whiz.reto.R
import com.whiz.core.base.BaseFragment
import com.whiz.core.util.isConnected
import com.whiz.reto.databinding.FragmentListMoviesBinding
import com.whiz.reto.presentation.feature.main.listmovies.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListMoviesFragment : BaseFragment() {

    private val viewModel: ListMoviesViewModel by viewModels()
    private lateinit var binding: FragmentListMoviesBinding

    companion object {
        const val ID_MOVIE = "ID_MOVIE"
    }

    private var adapter = MovieAdapter(
        MovieAdapter.MovieClickListener(
            onClick = { movie ->
                val bundle = Bundle()
                bundle.putInt(ID_MOVIE, movie.id)
                findNavController().navigate(
                    R.id.action_listMoviesFragment_to_detailMovieFragment,
                    bundle
                )
            },
            onSeeMore = {
                viewModel.loadMoreMovies(requireContext().isConnected())
            }
        ))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvMovies()
        initObserver()
        viewModel.loadMoreMovies(requireContext().isConnected())
    }

    override fun initObserver() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.loadingStateLivaData.observe(viewLifecycleOwner) {
            handleVisibilityProgressLoadStates(it)
        }

        viewModel.mutableException.observe(viewLifecycleOwner) {
            validateException(it)
        }

    }

    private fun initRvMovies() {
        binding.rvMovies.adapter = adapter
    }


}