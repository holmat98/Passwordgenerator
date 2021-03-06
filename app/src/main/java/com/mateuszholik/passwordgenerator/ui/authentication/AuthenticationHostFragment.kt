package com.mateuszholik.passwordgenerator.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentAuthenticationHostBinding
import com.mateuszholik.passwordgenerator.ui.authentication.factories.FragmentFactory
import com.mateuszholik.passwordgenerator.ui.authentication.models.AuthenticationScreens
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AuthenticationHostFragment : BaseFragment() {

    private val viewModel: AuthenticationHostViewModel by sharedViewModel()
    private val fragmentFactory: FragmentFactory by inject()
    private var binding: FragmentAuthenticationHostBinding? = null

    override val isBottomNavVisible: Boolean
        get() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAuthenticationHostBinding?>(
            inflater,
            R.layout.fragment_authentication_host,
            container,
            false
        ).apply {
            viewModel = this@AuthenticationHostFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentScreen.observe(viewLifecycleOwner) {
            replaceCurrentScreen(it)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun replaceCurrentScreen(newScreen: AuthenticationScreens) {
        val binding = binding ?: return
        requireActivity().supportFragmentManager.commit {
            replace(
                binding.fragmentHost.id,
                fragmentFactory.create(newScreen)
            )
        }
    }
}