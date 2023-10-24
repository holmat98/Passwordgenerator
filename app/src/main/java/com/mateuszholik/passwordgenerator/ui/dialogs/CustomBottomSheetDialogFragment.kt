package com.mateuszholik.passwordgenerator.ui.dialogs

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.R
import com.mateuszholik.passwordgenerator.databinding.DialogCustomBottomSheetBinding
import com.mateuszholik.passwordgenerator.extensions.fromParcelable
import kotlinx.parcelize.Parcelize

class CustomBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var binding: DialogCustomBottomSheetBinding? = null

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
        binding = DialogCustomBottomSheetBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
    }

    private fun setUpViews() {
        arguments?.let {
            val title = it.getString(TITLE_TEXT_KEY)
            val firstButtonSetup: ButtonSetup? =
                it.fromParcelable(FIRST_BUTTON_KEY, ButtonSetup::class.java)
            val secondButtonSetup: ButtonSetup? =
                it.fromParcelable(SECOND_BUTTON_KEY, ButtonSetup::class.java)
            val thirdButtonSetup: ButtonSetup? =
                it.fromParcelable(THIRD_BUTTON_KEY, ButtonSetup::class.java)

            binding?.run {
                titleText.text = title
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
            title: String,
            listener: Listener,
            firstButtonSetup: ButtonSetup? = null,
            secondButtonSetup: ButtonSetup? = null,
            thirdButtonSetup: ButtonSetup? = null,
        ): CustomBottomSheetDialogFragment =
            CustomBottomSheetDialogFragment().apply {
                arguments = bundleOf(TITLE_TEXT_KEY to title)
                arguments?.putParcelable(FIRST_BUTTON_KEY, firstButtonSetup)
                arguments?.putParcelable(SECOND_BUTTON_KEY, secondButtonSetup)
                arguments?.putParcelable(THIRD_BUTTON_KEY, thirdButtonSetup)
                this.listener = listener
            }
    }
}
