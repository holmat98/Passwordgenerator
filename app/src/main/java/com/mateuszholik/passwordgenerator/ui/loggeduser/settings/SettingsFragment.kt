package com.mateuszholik.passwordgenerator.ui.loggeduser.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentSettingsBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment() {

    override val isBottomNavVisible: Boolean = true

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModel()

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
}