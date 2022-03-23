package com.mateuszholik.passwordgenerator.di

import com.mateuszholik.data.di.dataModules
import com.mateuszholik.domain.di.domainModules
import com.mateuszholik.passwordgenerator.di.modules.viewModelModule
import org.koin.core.module.Module

private val appModules = listOf<Module>(
    viewModelModule
)

val allModules = dataModules + domainModules + appModules