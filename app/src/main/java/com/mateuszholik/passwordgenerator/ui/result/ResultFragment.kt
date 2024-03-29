package com.mateuszholik.passwordgenerator.ui.result

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordsExportResultBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment

class ResultFragment : BaseFragment(R.layout.fragment_passwords_export_result) {

    private val binding by viewBinding(FragmentPasswordsExportResultBinding::bind)
    private val navArgs: ResultFragmentArgs by navArgs()

    override val isBottomNavVisible: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews(navArgs.wasExportSuccessfull)
        setUpButton()
    }

    private fun setUpButton() {
        binding.confirmButton.setOnClickListener {
            findNavController().navigate(R.id.action_result_to_settings)
        }
    }

    private fun setUpViews(wasSuccessful: Boolean) {
        with(binding) {
            exportResultHeader.text = context?.getString(navArgs.headerText)
            exportResultDescription.text = context?.getString(navArgs.descriptionText)
            animation.setAnimation(
                if (wasSuccessful) {
                    R.raw.anim_success
                } else {
                    R.raw.anim_failure
                }
            )
            animation.playAnimation()
        }
    }
}
