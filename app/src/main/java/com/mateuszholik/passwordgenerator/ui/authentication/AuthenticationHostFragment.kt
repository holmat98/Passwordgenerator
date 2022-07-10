package com.mateuszholik.passwordgenerator.ui.authentication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentAuthenticationHostBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.ui.authentication.factories.FragmentFactory
import com.mateuszholik.passwordgenerator.ui.authentication.models.AuthenticationScreens
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AuthenticationHostFragment : BaseFragment(R.layout.fragment_authentication_host) {

    private val viewModel: AuthenticationHostViewModel by sharedViewModel()
    private val fragmentFactory: FragmentFactory by inject()
    private val binding by viewBinding(FragmentAuthenticationHostBinding::bind)

    override val isBottomNavVisible: Boolean
        get() = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = this@AuthenticationHostFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.currentScreen.observe(viewLifecycleOwner) {
            replaceCurrentScreen(it)
        }
    }

    private fun replaceCurrentScreen(newScreen: AuthenticationScreens) {
        requireActivity().supportFragmentManager.commit {
            replace(
                binding.fragmentHost.id,
                fragmentFactory.create(newScreen)
            )
        }
    }
}