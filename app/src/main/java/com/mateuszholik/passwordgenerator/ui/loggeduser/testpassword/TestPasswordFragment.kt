package com.mateuszholik.passwordgenerator.ui.loggeduser.testpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.databinding.FragmentTestPasswordBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment

class TestPasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentTestPasswordBinding

    override val isBottomNavVisible: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestPasswordBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testPasswordButton.setOnClickListener {
            navigateToPasswordScoreScreen(binding.testPasswordValueET.text.toString())
        }
    }

    private fun navigateToPasswordScoreScreen(password: String) {
        val action =
            TestPasswordFragmentDirections.actionTestPasswordToPasswordScoreFragment(password)
        findNavController().navigate(action)
    }
}