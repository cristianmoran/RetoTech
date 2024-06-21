package com.whiz.core.base

import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheet : BottomSheetDialogFragment() {

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, this::class.java.name)
    }

}
