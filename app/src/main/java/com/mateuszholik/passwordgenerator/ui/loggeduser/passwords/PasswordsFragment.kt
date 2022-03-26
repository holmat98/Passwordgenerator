package com.mateuszholik.passwordgenerator.ui.loggeduser.passwords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordsBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment

class PasswordsFragment : BaseFragment() {

    private lateinit var binding: FragmentPasswordsBinding

    override val isBottomNavVisible: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordsBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }
}