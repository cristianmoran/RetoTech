package com.whiz.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiz.core.SingleLiveEvent
import com.whiz.core.network.EventResult
import com.whiz.core.uimodel.UiLoadState

abstract class BaseViewModel() : ViewModel() {

//    internal var messageSnackLiveData: MutableLiveData<UiMessageSnack> = MutableLiveData()

    internal val messageException = MutableLiveData<String>()
    val loadingStateLivaData = SingleLiveEvent<UiLoadState>()
    var mutableException = SingleLiveEvent<EventResult.Failure>()

    fun managementException(response: EventResult.Failure) {
        mutableException.postValue(response)
    }

}