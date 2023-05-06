package com.mateuszholik.passwordgenerator.ui.dialogs

import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.DialogThreeButtonsBinding
import kotlinx.parcelize.Parcelize

class ThreeButtonsBottomSheetDialog : BottomSheetDialogFragment() {

    private var binding: DialogThreeButtonsBinding? = null

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
        binding = DialogThreeButtonsBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
    }

    private fun setUpViews() {
        arguments?.let {
            val titleResId = it.getInt(TITLE_TEXT_KEY)
            val firstButtonSetup: ButtonSetup? = it.getParcelable(FIRST_BUTTON_KEY)
            val secondButtonSetup: ButtonSetup? = it.getParcelable(SECOND_BUTTON_KEY)
            val thirdButtonSetup: ButtonSetup? = it.getParcelable(THIRD_BUTTON_KEY)

            binding?.run {
                titleText.text = context?.getString(titleResId)
                setUpButton(firstButton, firstButtonSetup) { listener?.onFirstButtonClicked() }
                setUpButton(secondButton, secondButtonSetup) { listener?.onSecondButtonClicked() }
                setUpButton(thirdButton, thirdButtonSetup) { listener?.onThirdButtonClicked() }
            }
        }
    }

    private fun setUpButton(
        button: MaterialButton,
        buttonSetup: ButtonSetup?,
        onClick: () -> Unit
    ) {
        buttonSetup?.let {
            button.apply {
                text = context.getString(buttonSetup.textResId)
                icon = ContextCompat.getDrawable(context, buttonSetup.iconResId)
                setOnClickListener {
                    onClick()
                    dismiss()
                }
            }
        } ?: run {
            button.isVisible = false
        }
    }

    interface Listener {

        fun onFirstButtonClicked()
        fun onSecondButtonClicked()
        fun onThirdButtonClicked()
    }

    @Parcelize
    data class ButtonSetup(
        @DrawableRes val iconResId: Int,
        @StringRes val textResId: Int,
    ) : Parcelable

    companion object {
        private const val FIRST_BUTTON_KEY = "FIRST_BUTTON_KEY"
        private const val SECOND_BUTTON_KEY = "SECOND_BUTTON_KEY"
        private const val THIRD_BUTTON_KEY = "THIRD_BUTTON_KEY"
        private const val TITLE_TEXT_KEY = "TEXT_KEY"

        fun newInstance(
            @StringRes titleResId: Int,
            listener: Listener,
            firstButtonSetup: ButtonSetup? = null,
            secondButtonSetup: ButtonSetup? = null,
            thirdButtonSetup: ButtonSetup? = null,
        ): ThreeButtonsBottomSheetDialog =
            ThreeButtonsBottomSheetDialog().apply {
                arguments = bundleOf(TITLE_TEXT_KEY to titleResId)
                arguments?.putParcelable(FIRST_BUTTON_KEY, firstButtonSetup)
                arguments?.putParcelable(SECOND_BUTTON_KEY, secondButtonSetup)
                arguments?.putParcelable(THIRD_BUTTON_KEY, thirdButtonSetup)
                this.listener = listener
            }
    }
}
