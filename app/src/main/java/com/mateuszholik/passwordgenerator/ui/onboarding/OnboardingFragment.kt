package com.mateuszholik.passwordgenerator.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mateuszholik.passwordgenerator.databinding.FragmentOnboardingBinding
import com.mateuszholik.passwordgenerator.ui.onboarding.adapter.OnboardingAdapter
import com.mateuszholik.passwordgenerator.ui.onboarding.model.OnboardingScreen

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val adapter = OnboardingAdapter()

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
    }

    private fun setUpViewPager() {
        adapter.submitList(OnboardingScreen.values().toList())
        binding.viewpager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { _, _ -> }.attach()
    }
}