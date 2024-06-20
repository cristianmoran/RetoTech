package com.whiz.reto.presentation.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.whiz.reto.databinding.AdapterMovieBinding
import com.whiz.reto.databinding.ItemSeeMorePedidoBinding
import com.whiz.reto.domain.entity.movies.Movie

class MovieAdapter(
    private val clickListener: MovieClickListener
) : ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    companion object {
        const val VIEW_TYPE_DATA = 0
        const val VIEW_TYPE_SEE_MORE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_DATA) {
            MovieViewHolder.from(parent)
        } else SeeMoreViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            (holder).bind(getItem(position), clickListener)
        } else if (holder is SeeMoreViewHolder) {
            (holder).bind(clickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_SEE_MORE else VIEW_TYPE_DATA
    }

    class MovieViewHolder private constructor(
        private val binding: AdapterMovieBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, clickListener: MovieClickListener) {
            binding.tvName.text = movie.url

            binding.container.setOnClickListener { clickListener.onClickItem(movie) }

        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = AdapterMovieBinding.inflate(inflater, parent, false)
                return MovieViewHolder(binding)
            }
        }
    }

    class SeeMoreViewHolder private constructor(private val binding: ItemSeeMorePedidoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: MovieClickListener) {
            binding.constraintLayout.setOnClickListener { clickListener.onClickSeeMore() }
        }

        companion object {
            fun from(parent: ViewGroup): SeeMoreViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemSeeMorePedidoBinding.inflate(inflater, parent, false)
                return SeeMoreViewHolder(binding)
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(
            oldRequest: Movie, newRequest: Movie
        ): Boolean {
            return oldRequest.url == newRequest.url
        }

        override fun areContentsTheSame(
            oldRequest: Movie, newRequest: Movie
        ): Boolean {
            return oldRequest == newRequest
        }
    }

    class MovieClickListener(
        private val onClick: ((item: Movie) -> Unit)? = null,
        private val onSeeMore: (() -> Unit)? = null
    ) {
        fun onClickItem(item: Movie) {
            onClick?.invoke(item)
        }

        fun onClickSeeMore() {
            onSeeMore?.invoke()
        }
    }

}
