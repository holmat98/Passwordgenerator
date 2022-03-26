package com.mateuszholik.passwordgenerator.ui.loggeduser.testpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
}