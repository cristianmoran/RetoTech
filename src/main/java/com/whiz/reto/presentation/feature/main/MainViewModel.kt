package com.whiz.reto.presentation.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.whiz.reto.core.BaseViewModel
import com.whiz.reto.core.uimodel.UiLoadState
import com.whiz.reto.domain.entity.movies.ListMovies
import com.whiz.reto.domain.entity.movies.Movie
import com.whiz.reto.domain.usecase.ListMoviesUseCase
import com.whiz.reto.network.EventResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val listMoviesUseCase: ListMoviesUseCase
) : BaseViewModel() {

    companion object {
        const val SIZE_PAGE = 25
    }

    private val _moviesLiveData: MutableLiveData<List<Movie?>> = MutableLiveData()
    val moviesLiveData: LiveData<List<Movie?>> get() = _moviesLiveData

    private var pageNumber = -1
    private var isConnected = false

    private fun getMovies() {
        viewModelScope.launch {
            loadingStateLivaData.postValue(UiLoadState.Loading)
            val offset = SIZE_PAGE * pageNumber

            when (val response = listMoviesUseCase.execute(offset, SIZE_PAGE, isConnected)) {
                is EventResult.Success -> managementListMovies(response)
                is EventResult.Failure -> managementException(response)
            }
            loadingStateLivaData.postValue(UiLoadState.Finished)
        }
    }

    private fun managementListMovies(response: EventResult.Success<ListMovies>) {
        val currentList =
            (_moviesLiveData.value
                ?: emptyList()).toMutableList()
        if (currentList.isNotEmpty()) currentList.removeLast()
        var data = (response.data as ListMovies).results
        val newList = data.toMutableList()
        if (newList.isNotEmpty()) {
            if (pageNumber == 0) currentList.clear()
            currentList.addAll(newList)
            if (pageNumber < response.data.count) currentList.add(null)
        }
        data = currentList
        _moviesLiveData.value = data
    }

    fun loadMoreMovies(isConnected: Boolean) {
        pageNumber += 1
        this.isConnected = isConnected
        getMovies()
    }


}