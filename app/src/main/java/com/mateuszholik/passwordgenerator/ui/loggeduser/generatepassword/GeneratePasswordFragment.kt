package com.mateuszholik.passwordgenerator.ui.loggeduser.generatepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mateuszholik.passwordgenerator.databinding.FragmentGeneratePasswordBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment

class GeneratePasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentGeneratePasswordBinding

    override val isBottomNavVisible: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeneratePasswordBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }
}