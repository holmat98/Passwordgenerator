package com.mateuszholik.passwordgenerator.ui.testpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.databinding.FragmentTestPasswordBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment

class TestPasswordFragment : BaseFragment() {

    private var binding: FragmentTestPasswordBinding? = null

    override val isBottomNavVisible: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestPasswordBinding.inflate(
            inflater,
            container,
            false
        )

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            testPasswordButton.setOnClickListener {
                navigateToPasswordScoreScreen(testPasswordValueET.text.toString())
                testPasswordValueET.text?.clear()
            }
            testPasswordValueET.doOnTextChanged { text, _, _, _ ->
                testPasswordButton.isEnabled = text.toString().isNotEmpty()
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun navigateToPasswordScoreScreen(password: String) {
        val action =
            TestPasswordFragmentDirections.actionTestPasswordToPasswordScoreFragment(password)
        findNavController().navigate(action)
    }
}