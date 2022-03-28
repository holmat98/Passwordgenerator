package com.mateuszholik.passwordgenerator.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.mateuszholik.passwordgenerator.databinding.FragmentAuthenticationHostBinding
import com.mateuszholik.passwordgenerator.ui.authentication.factories.FragmentFactory
import com.mateuszholik.passwordgenerator.ui.authentication.models.AuthenticationScreens
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AuthenticationHostFragment : BaseFragment() {

    private val viewModel: AuthenticationHostViewModel by sharedViewModel()
    private val fragmentFactory: FragmentFactory by inject()
    private lateinit var binding: FragmentAuthenticationHostBinding

    override val isBottomNavVisible: Boolean
        get() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthenticationHostBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentScreen.observe(viewLifecycleOwner) {
            replaceCurrentScreen(it)
        }
    }

    private fun replaceCurrentScreen(newScreen: AuthenticationScreens) {
        requireActivity().supportFragmentManager.beginTransaction().replace(
            binding.fragmentHost.id,
            fragmentFactory.create(newScreen)
        ).commit()
    }
}