package com.whiz.reto.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiz.reto.core.network.EventResult
import com.whiz.reto.core.uimodel.UiLoadState

abstract class BaseViewModel() : ViewModel() {

//    internal var messageSnackLiveData: MutableLiveData<UiMessageSnack> = MutableLiveData()

    internal val messageException = MutableLiveData<String>()
    internal val loadingStateLivaData = SingleLiveEvent<UiLoadState>()
    internal var mutableException = SingleLiveEvent<EventResult.Failure>()

    fun managementException(response: EventResult.Failure) {
        mutableException.postValue(response)
    }

}