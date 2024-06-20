package com.whiz.reto.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiz.reto.core.uimodel.UiLoadState
import com.whiz.reto.network.EventResult

abstract class BaseViewModel() : ViewModel() {

//    internal var messageSnackLiveData: MutableLiveData<UiMessageSnack> = MutableLiveData()

    internal val messageException = MutableLiveData<String>()
    internal val loadingStateLivaData = SingleLiveEvent<UiLoadState>()
    internal var mutableException = SingleLiveEvent<EventResult.Failure>()

}