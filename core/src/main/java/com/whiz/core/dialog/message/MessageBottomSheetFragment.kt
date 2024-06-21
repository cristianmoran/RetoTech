package com.whiz.core.dialog.message

import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.whiz.core.base.BaseBottomSheet
import com.whiz.core.databinding.FragmentMessageBottomSheetBinding
import com.whiz.core.model.MessageDialogModel

class MessageBottomSheetFragment : BaseBottomSheet() {
    private lateinit var binding: FragmentMessageBottomSheetBinding
    private var messageDialogModel: MessageDialogModel? = null
    private var actionListener: ActionListener? = null

    companion object {
        fun newInstance(
            messageDialogModel: MessageDialogModel,
            onDismiss: (() -> Unit)? = null
        ): MessageBottomSheetFragment {
            return MessageBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MESSAGE_DIALOG_MODEL, messageDialogModel)
                    onDismiss?.let {
                        putParcelable(MESSAGE_DIALOG_ACTION, object : ActionListener {
                            override fun action() {
                                onDismiss.invoke()
                            }
                        })
                    }
                }
            }
        }

        private interface ActionListener : Parcelable {
            fun action()
            override fun describeContents(): Int = 0
            override fun writeToParcel(dest: Parcel, flags: Int) {}
        }

        private const val MESSAGE_DIALOG_MODEL = "MESSAGE_DIALOG_MODEL"
        private const val MESSAGE_DIALOG_ACTION = "MESSAGE_DIALOG_ACTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            messageDialogModel = it.getParcelable(MESSAGE_DIALOG_MODEL)
            actionListener = it.getParcelable(MESSAGE_DIALOG_ACTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.messageTextView.text = messageDialogModel?.message
        messageDialogModel?.image?.let {
            binding.iconImageView.setImageResource(it)
        }
        messageDialogModel?.color?.let {
            binding.messageTextView.setTextColor(ContextCompat.getColor(requireContext(), it))
        }
        binding.actionButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        actionListener?.action()
        super.onDismiss(dialog)
    }

}