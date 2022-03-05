package com.mateuszholik.passwordgenerator.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentOnboardingBinding
import com.mateuszholik.passwordgenerator.ui.onboarding.adapter.OnboardingAdapter
import com.mateuszholik.passwordgenerator.ui.onboarding.model.OnboardingScreen

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val adapter = OnboardingAdapter(::navigateToNextScreen)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPager()

        binding.skipButton.setOnClickListener {
            navigateToNextScreen()
        }
    }

    private fun setUpViewPager() {
        adapter.submitList(OnboardingScreen.values().toList())
        with(binding) {
            viewpager.adapter = adapter
            TabLayoutMediator(tabLayout, viewpager) { _, _ -> }.attach()
        }
    }

    private fun navigateToNextScreen() {
        findNavController().navigate(R.id.action_onboardingFragment_to_createPasswordFragment)
    }
}