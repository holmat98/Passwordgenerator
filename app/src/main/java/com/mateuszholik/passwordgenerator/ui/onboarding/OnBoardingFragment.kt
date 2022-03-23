package com.mateuszholik.passwordgenerator.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentOnboardingBinding
import com.mateuszholik.passwordgenerator.extensions.hide
import com.mateuszholik.passwordgenerator.extensions.show
import com.mateuszholik.passwordgenerator.ui.onboarding.adapter.OnBoardingAdapter
import com.mateuszholik.passwordgenerator.ui.onboarding.model.OnBoardingScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val onBoardingScreens = OnBoardingScreen.values().toList()
    private val adapter = OnBoardingAdapter()
    private val viewModel: OnBoardingViewModel by viewModel()

    private val onPageChangedCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            if (position == onBoardingScreens.size - 1) {
                binding.skipButton.hide()
                updateNextButton(R.string.button_start, ::navigateToCreatePinScreen)
            } else {
                if (!binding.skipButton.isVisible) {
                    binding.skipButton.show()
                }
                updateNextButton(R.string.button_next) {
                    binding.viewpager.apply {
                        setCurrentItem(currentItem + 1, true)
                    }
                }
            }
        }
    }

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

        setUpObservers()

        setUpViewPager()

        binding.skipButton.setOnClickListener {
            navigateToCreatePinScreen()
        }
    }

    override fun onDestroy() {
        binding.viewpager.unregisterOnPageChangeCallback(onPageChangedCallback)
        super.onDestroy()
    }

    private fun setUpViewPager() {
        adapter.submitList(onBoardingScreens)
        with(binding) {
            viewpager.adapter = adapter
            viewpager.registerOnPageChangeCallback(onPageChangedCallback)
            TabLayoutMediator(tabLayout, viewpager) { _, _ -> }.attach()
        }
    }

    private fun updateNextButton(@StringRes newText: Int, onClick: () -> Unit) {
        binding.nextButton.apply {
            text = getString(newText)
            setOnClickListener { onClick() }
        }
    }

    private fun setUpObservers() {
        viewModel.shouldGoToNextScreen.observe(viewLifecycleOwner) { shouldSkipOnBoarding ->
            if (shouldSkipOnBoarding) {
                findNavController().navigate(R.id.action_onboardingFragment_to_logInFragment)
            }
        }
    }

    private fun navigateToCreatePinScreen() {
        findNavController().navigate(R.id.action_onboardingFragment_to_createPasswordFragment)
    }
}