package com.mateuszholik.passwordgenerator.ui.onboarding.model

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.mateuszholik.passwordgenerator.R

enum class OnboardingScreen(
    @StringRes val text: Int,
    @RawRes val animation: Int
) {

    SCREEN_1(
        R.string.onboarding_screen_1,
        R.raw.save_passwords
    ),
    SCREEN_2(
        R.string.onboarding_screen_2,
        R.raw.create_passwords
    ),
    SCREEN_3(
        R.string.onboarding_screen_3,
        R.raw.test_passwords
    )
}