package com.mateuszholik.passwordgenerator.ui.authentication.onboarding.model

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.mateuszholik.passwordgenerator.R

enum class OnBoardingScreen(
    @StringRes val text: Int,
    @RawRes val animation: Int
) {

    SCREEN_1(
        R.string.onboarding_text_screen_1,
        R.raw.save_passwords
    ),
    SCREEN_2(
        R.string.onboarding_text_screen_2,
        R.raw.create_passwords
    ),
    SCREEN_3(
        R.string.onboarding_text_screen_3,
        R.raw.test_passwords
    )
}