package com.mateuszholik.passwordgenerator.ui.result

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordsExportResultBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment

class PasswordsExportResult : BaseFragment(R.layout.fragment_passwords_export_result) {

    private val binding by viewBinding(FragmentPasswordsExportResultBinding::bind)
    private val navArgs: PasswordsExportResultArgs by navArgs()

    override val isBottomNavVisible: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews(navArgs.wasExportSuccessfull)
        setUpButton()
    }

    private fun setUpButton() {
        binding.confirmButton.setOnClickListener {
            findNavController().navigate(R.id.action_passwordsExportResult_to_settings)
        }
    }

    private fun setUpViews(wasExportSuccessful: Boolean) {
        with(binding) {
            if (wasExportSuccessful) {
                animation.setAnimation(R.raw.anim_success)
                exportResultHeader.text = context?.getString(R.string.export_result_screen_success)
            } else {
                animation.setAnimation(R.raw.anim_failure)
                exportResultHeader.text = context?.getString(R.string.export_result_screen_failure)
            }
            animation.playAnimation()
        }
    }
}