package com.mateuszholik.passwordgenerator.ui.onboarding.model

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.mateuszholik.passwordgenerator.R

enum class OnboardingScreen(
    @StringRes val title: Int,
    @StringRes val text: Int,
    @RawRes val animation: Int,
    val isButtonVisible: Boolean
) {

    SCREEN_1(
        R.string.onboarding_title_screen_1,
        R.string.onboarding_text_screen_1,
        R.raw.save_passwords,
        false
    ),
    SCREEN_2(
        R.string.onboarding_title_screen_2,
        R.string.onboarding_text_screen_2,
        R.raw.create_passwords,
        false
    ),
    SCREEN_3(
        R.string.onboarding_title_screen_3,
        R.string.onboarding_text_screen_3,
        R.raw.test_passwords,
        true
    )
}