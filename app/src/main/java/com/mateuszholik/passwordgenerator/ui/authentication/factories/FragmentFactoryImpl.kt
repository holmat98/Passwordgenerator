package com.mateuszholik.passwordgenerator.ui.authentication.factories

import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.ui.authentication.biometric.BiometricAuthenticationFragment
import com.mateuszholik.passwordgenerator.ui.authentication.createpin.CreatePinFragment
import com.mateuszholik.passwordgenerator.ui.authentication.login.LogInFragment
import com.mateuszholik.passwordgenerator.ui.authentication.models.AuthenticationScreens

class FragmentFactoryImpl : FragmentFactory {

    override fun create(screen: AuthenticationScreens): Fragment =
        when (screen) {
            AuthenticationScreens.LOG_IN -> LogInFragment.newInstance()
            AuthenticationScreens.BIOMETRIC_LOG_IN -> BiometricAuthenticationFragment.newInstance()
            AuthenticationScreens.CREATE_PIN -> CreatePinFragment.newInstance()
        }
}