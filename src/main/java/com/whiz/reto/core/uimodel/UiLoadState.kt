package com.whiz.reto.core.uimodel
 sealed class UiLoadState {
    object Loading : UiLoadState()
    object Finished : UiLoadState()
}