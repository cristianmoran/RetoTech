package com.whiz.core.uimodel
 sealed class UiLoadState {
    object Loading : UiLoadState()
    object Finished : UiLoadState()
}