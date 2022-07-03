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

        setUpViews()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setUpViews() {
        val binding = binding ?: return
        binding.testPasswordButton.setOnClickListener {
            navigateToPasswordScoreScreen(binding.testPasswordValueET.text.toString())
            binding.testPasswordValueET.text?.clear()
        }
        binding.testPasswordValueET.doOnTextChanged { text, _, _, _ ->
            binding.testPasswordButton.isEnabled = text.toString().isNotEmpty()
        }
    }

    private fun navigateToPasswordScoreScreen(password: String) {
        val action =
            TestPasswordFragmentDirections.actionTestPasswordToPasswordScoreFragment(password)
        findNavController().navigate(action)
    }
}