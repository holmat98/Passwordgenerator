package com.mateuszholik.passwordgenerator.ui.authentication.factories

import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.ui.authentication.createpin.CreatePinFragment
import com.mateuszholik.passwordgenerator.ui.authentication.login.LogInFragment
import com.mateuszholik.passwordgenerator.ui.authentication.models.AuthenticationScreens
import com.mateuszholik.passwordgenerator.ui.authentication.onboarding.OnBoardingFragment

class FragmentFactoryImpl : FragmentFactory {

    override fun create(screen: AuthenticationScreens): Fragment =
        when (screen) {
            AuthenticationScreens.ON_BOARDING -> OnBoardingFragment.newInstance()
            AuthenticationScreens.LOG_IN -> LogInFragment.newInstance()
            AuthenticationScreens.CREATE_PIN -> CreatePinFragment.newInstance()
        }
}