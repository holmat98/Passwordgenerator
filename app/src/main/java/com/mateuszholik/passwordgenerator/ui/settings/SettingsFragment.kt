package com.mateuszholik.passwordgenerator.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentSettingsBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.extensions.showNumberPickerDialog
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SettingsFragment : BaseFragment() {

    override val isBottomNavVisible: Boolean = true

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModel()
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentSettingsBinding?>(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        ).apply {
            viewModel = this@SettingsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()

        binding.passwordValidityButton.setOnClickListener {
            showNumberPickerDialog(
                R.string.settings_dialog_password_validity,
                minValue = MIN_PASSWORD_VALIDITY,
                maxValue = MAX_PASSWORD_VALIDITY
            ) {
                viewModel.savePasswordValidity(it.toLong())
            }
        }
    }

    private fun setUpObservers() {
        viewModel.errorOccurred.observe(viewLifecycleOwner) {
            messageProvider.show(it)
        }
    }

    private companion object {
        const val MIN_PASSWORD_VALIDITY = 30
        const val MAX_PASSWORD_VALIDITY = 120
    }
}