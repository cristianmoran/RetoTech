package com.whiz.core.base

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.whiz.core.R
import com.whiz.core.dialog.message.MessageBottomSheetFragment
import com.whiz.core.entity.ErrorRetrofitType
import com.whiz.core.model.MessageDialogModel
import com.whiz.core.model.MessageError
import com.whiz.core.network.EventResult
import com.whiz.core.uimodel.UiLoadState

abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: BaseDialog = BaseDialog()
    private var progressInitialized = false

    private fun showProgressDialog() {
        if (!progressInitialized) {
            progressDialog.isCancelable = false
            progressInitialized = true
            progressDialog.show(supportFragmentManager, "")
        }
    }

    private fun hideProgressDialog() {
        if (progressInitialized) {
            progressDialog.dismiss()
            progressInitialized = false
        }
    }

    fun hideKeyBoard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showMessageSnack(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .show()
    }

    fun showMessageException(message: String) {
        MessageBottomSheetFragment.newInstance(
            MessageDialogModel(message, R.drawable.ic_error_large)
        ).show(supportFragmentManager)
    }

    fun showMessageToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun handleVisibilityProgressLoadStates(it: UiLoadState?) {
        return when (it) {
            UiLoadState.Loading -> showProgressDialog()
            else -> hideProgressDialog()
        }
    }

    fun validateException(eventResult: EventResult.Failure) {
        var messageError = MessageError.GENERAL_ERROR_MESSAGE
        try {
            when (eventResult.type) {
                ErrorRetrofitType.AIRPLANE_ACTIVE -> {
                    MessageBottomSheetFragment.newInstance(
                        MessageDialogModel(
                            MessageError.AIRPLANE_ERROR_MESSAGE,
                            R.drawable.ic_error_large
                        )
                    ).show(supportFragmentManager)
                }

                ErrorRetrofitType.EXCEPTION -> {
                    eventResult.responseError?.apiMensaje?.let {
                        messageError = it
                    }
                    MessageBottomSheetFragment.newInstance(
                        MessageDialogModel(
                            messageError,
                            R.drawable.ic_error_large
                        )
                    ).show(supportFragmentManager)
                }

                ErrorRetrofitType.NETWORK_EXCEPTION -> {
                    MessageBottomSheetFragment.newInstance(
                        MessageDialogModel(
                            MessageError.NETWORK_ERROR,
                            R.drawable.ic_error_wifi
                        )
                    ).show(supportFragmentManager)
                }

                ErrorRetrofitType.UNAUTHORIZED -> {
                    val dialog = MessageBottomSheetFragment.newInstance(
                        MessageDialogModel(
                            MessageError.UNAUTHORIZE_MESSAGE,
                            R.drawable.ic_error_large
                        ), ::navigateToSignIn
                    )
                    dialog.isCancelable = false
                    dialog.show(supportFragmentManager)
                }
            }
        } catch (e: Exception) {
            MessageBottomSheetFragment.newInstance(
                MessageDialogModel(
                    MessageError.GENERAL_ERROR_MESSAGE,
                    R.drawable.ic_error_large
                )
            ).show(supportFragmentManager)
        }
    }


    private fun navigateToSignIn() {
//        startActivity(AuthActivity.newInstance(this, true))
        finishAffinity()
    }

}
