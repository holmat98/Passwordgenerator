package com.mateuszholik.passwordgenerator.factories

import androidx.navigation.ui.AppBarConfiguration
import com.mateuszholik.passwordgenerator.R

interface AppBarConfigurationFactory {

    fun create(): AppBarConfiguration
}

class AppBarConfigurationFactoryImpl : AppBarConfigurationFactory {

    override fun create(): AppBarConfiguration = AppBarConfiguration(
        setOf(
            R.id.logInFragment,
            R.id.passwords,
            R.id.generatePassword,
            R.id.testPassword,
            R.id.settings
        )
    )
}