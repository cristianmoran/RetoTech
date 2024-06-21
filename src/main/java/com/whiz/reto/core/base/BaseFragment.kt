package com.whiz.reto.core.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.whiz.reto.core.network.EventResult
import com.whiz.reto.core.uimodel.UiLoadState

abstract class BaseFragment : Fragment() {

    private var progressDialog: BaseDialog = BaseDialog()
    private var progressIniciado = false

    fun finish() = requireActivity().finish()

    fun closedFragment() = findNavController().navigateUp()

    fun finishAffinity() = requireActivity().finishAffinity()

    abstract fun initObserver()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    fun showMessageException(message: String) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showMessageException(message)
        }
    }

    fun handleVisibilityProgressLoadStates(it: UiLoadState?) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).handleVisibilityProgressLoadStates(it)
        }
    }

    fun validateException(eventResult: EventResult.Failure) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).validateException(eventResult)
        }
    }

}