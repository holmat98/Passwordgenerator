package com.mateuszholik.passwordgenerator.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.DialogGeneratedPasswordBinding

class CustomBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var binding: DialogGeneratedPasswordBinding? = null

    var listener: Listener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_Material3_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogGeneratedPasswordBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
    }

    private fun setUpViews() {
        arguments?.let {
            val firstButtonRes = it.getInt(FIRST_BUTTON_IMAGE_RES_KEY)
            val secondButtonRes = it.getInt(SECOND_BUTTON_IMAGE_RES_KEY)
            val text = it.getString(TEXT_KEY)

            setUpLeftButton(firstButtonRes)
            setUpRightButton(secondButtonRes)
            binding?.text?.text = text
        }
    }

    private fun setUpLeftButton(imageRes: Int) {
        binding?.leftButton?.apply {
            setImageDrawable(ContextCompat.getDrawable(requireContext(), imageRes))
            setOnClickListener {
                listener?.onFirstButtonClicked()
                dismiss()
            }
        }
    }

    private fun setUpRightButton(imageRes: Int) {
        binding?.rightButton?.apply {
            setImageDrawable(ContextCompat.getDrawable(requireContext(), imageRes))
            setOnClickListener {
                listener?.onSecondButtonClicked()
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        listener = null
        super.onDestroy()
    }

    interface Listener {

        fun onFirstButtonClicked()
        fun onSecondButtonClicked()
    }

    companion object {
        private const val FIRST_BUTTON_IMAGE_RES_KEY = "FIRST_BUTTON_IMAGE_RES_KEY"
        private const val SECOND_BUTTON_IMAGE_RES_KEY = "SECOND_BUTTON_IMAGE_RES_KEY"
        private const val TEXT_KEY = "TEXT_KEY"

        fun newInstance(
            @DrawableRes firstButtonImageRes: Int,
            @DrawableRes secondButtonImageRes: Int,
            text: String,
            listener: Listener
        ): CustomBottomSheetDialogFragment =
            CustomBottomSheetDialogFragment().apply {
                arguments = bundleOf(
                    FIRST_BUTTON_IMAGE_RES_KEY to firstButtonImageRes,
                    SECOND_BUTTON_IMAGE_RES_KEY to secondButtonImageRes,
                    TEXT_KEY to text
                )
                this.listener = listener
            }
    }

}
