package com.mateuszholik.passwordgenerator.ui.authentication.factories

import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.ui.authentication.models.AuthenticationScreens

interface FragmentFactory {

    fun create(screen: AuthenticationScreens): Fragment
}