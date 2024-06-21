package com.whiz.reto.presentation.feature.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.whiz.core.base.BaseViewModel
import com.whiz.core.uimodel.UiLoadState
import com.whiz.reto.domain.entity.movies.DetailMovie
import com.whiz.reto.domain.usecase.GetMovieDetailUseCase
import com.whiz.core.network.EventResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val detailUseCase: GetMovieDetailUseCase
) : com.whiz.core.base.BaseViewModel() {


    companion object {

    }

    private val _movieDetailLiveData: MutableLiveData<DetailMovie?> = MutableLiveData()
    val movieDetailLiveData: LiveData<DetailMovie?> get() = _movieDetailLiveData

    fun getMovieDetail(id: Int, isConnected: Boolean) {
        viewModelScope.launch {
            loadingStateLivaData.postValue(com.whiz.core.uimodel.UiLoadState.Loading)

            when (val response = detailUseCase.execute(id, isConnected)) {
                is com.whiz.core.network.EventResult.Success -> managementMovieDetail(response.data)
                is com.whiz.core.network.EventResult.Failure -> managementException(response)
            }
            loadingStateLivaData.postValue(com.whiz.core.uimodel.UiLoadState.Finished)
        }
    }

    private fun managementMovieDetail(detailMovie: DetailMovie?) {
        _movieDetailLiveData.postValue(detailMovie)
    }

}