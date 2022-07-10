package com.mateuszholik.passwordgenerator.ui.testpassword

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentTestPasswordBinding
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment

class TestPasswordFragment : BaseFragment(R.layout.fragment_test_password) {

    private val binding by viewBinding(FragmentTestPasswordBinding::bind)

    override val isBottomNavVisible: Boolean
        get() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            testPasswordButton.setOnClickListener {
                navigateToPasswordScoreScreen(testPasswordValueET.text.toString())
                testPasswordValueET.text?.clear()
            }
            testPasswordValueET.doOnTextChanged { text, _, _, _ ->
                testPasswordButton.isEnabled = text.toString().isNotEmpty()
            }
        }
    }

    private fun navigateToPasswordScoreScreen(password: String) {
        val action =
            TestPasswordFragmentDirections.actionTestPasswordToPasswordScoreFragment(password)
        findNavController().navigate(action)
    }
}