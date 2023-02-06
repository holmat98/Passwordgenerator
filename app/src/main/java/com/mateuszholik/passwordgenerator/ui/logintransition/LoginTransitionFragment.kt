package com.mateuszholik.passwordgenerator.ui.logintransition

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginTransitionFragment : BaseFragment(R.layout.fragment_login_transition) {

    private val viewModel: LoginTransitionViewModel by viewModel()

    override val isBottomNavVisible: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isPinCreated.observe(viewLifecycleOwner) { isPinCreated ->
            if (isPinCreated) {
                findNavController().navigate(R.id.action_loginTransitionFragment_to_logInFragment)
            } else {
                findNavController().navigate(R.id.action_loginTransitionFragment_to_createPinFragment)
            }
        }
    }
}
