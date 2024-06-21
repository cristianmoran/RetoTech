package com.whiz.core.dialog.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.whiz.core.base.BaseDialog
import com.whiz.core.databinding.DialogMessageBinding
import com.whiz.core.extensions.updateImageDrawable
import java.io.Serializable

class MessageDialog : BaseDialog() {
    private var imageSrc: Int? = null
    private var messageText: String? = null
    private var actionButtonText: String? = null
    private var onClickActionButton: () -> Unit = {}

    private lateinit var binding: DialogMessageBinding

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            if (containsKey(IMAGE_KEY)) imageSrc = getInt(IMAGE_KEY)
            if (containsKey(MESSAGE_TEXT_KEY)) messageText = getString(MESSAGE_TEXT_KEY)
            if (containsKey(ACTION_TEXT_KEY)) actionButtonText = getString(ACTION_TEXT_KEY)
            if (containsKey(ACTION_LAMBDA_KEY)) onClickActionButton =
                getSerializable(ACTION_LAMBDA_KEY) as () -> Unit
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            imageSrc?.also { ivIcon.updateImageDrawable(it) }
            messageText?.also { tvMessage.text = it }
            actionButtonText?.also { btnAction.text = it }

            btnAction.setOnClickListener {
                onClickActionButton.invoke()
                dismiss()
            }
        }

    }

    companion object {
        private const val IMAGE_KEY = "image_key"
        private const val MESSAGE_TEXT_KEY = "message_text_key"
        private const val ACTION_TEXT_KEY = "action_text_key"
        private const val ACTION_LAMBDA_KEY = "action_lambda_key"

        fun newInstance(
            imgSrc: Int? = null,
            messageText: String? = null,
            actionButtonText: String? = null,
            onClickYesButton: (() -> Unit)? = null
        ) =
            MessageDialog().apply {
                arguments = Bundle().apply {
                    imgSrc?.also { putInt(IMAGE_KEY, it) }
                    messageText?.also { putString(MESSAGE_TEXT_KEY, it) }
                    actionButtonText?.also { putString(ACTION_TEXT_KEY, it) }
                    onClickYesButton?.also {
                        putSerializable(
                            ACTION_LAMBDA_KEY,
                            it as Serializable
                        )
                    }
                }
            }
    }

}