package com.mateuszholik.passwordgenerator.ui.loggeduser.generatepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.databinding.FragmentGeneratePasswordBinding

class GeneratePasswordFragment : Fragment() {

    private lateinit var binding: FragmentGeneratePasswordBinding

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