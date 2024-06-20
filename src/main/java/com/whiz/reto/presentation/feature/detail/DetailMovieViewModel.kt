package com.whiz.reto.presentation.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.whiz.reto.core.BaseViewModel
import com.whiz.reto.core.uimodel.UiLoadState
import com.whiz.reto.domain.entity.movies.DetailMovie
import com.whiz.reto.domain.usecase.GetMovieDetailUseCase
import com.whiz.reto.core.network.EventResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val detailUseCase: GetMovieDetailUseCase
) : BaseViewModel() {


    companion object {

    }

    private val _movieDetailLiveData: MutableLiveData<DetailMovie?> = MutableLiveData()
    val movieDetailLiveData: LiveData<DetailMovie?> get() = _movieDetailLiveData

    fun getMovieDetail(id: Int, isConnected: Boolean) {
        viewModelScope.launch {
            loadingStateLivaData.postValue(UiLoadState.Loading)

            when (val response = detailUseCase.execute(id, isConnected)) {
                is EventResult.Success -> managementMovieDetail(response.data)
                is EventResult.Failure -> managementException(response)
            }
            loadingStateLivaData.postValue(UiLoadState.Finished)
        }
    }

    private fun managementMovieDetail(detailMovie: DetailMovie?) {
        _movieDetailLiveData.postValue(detailMovie)
    }

}