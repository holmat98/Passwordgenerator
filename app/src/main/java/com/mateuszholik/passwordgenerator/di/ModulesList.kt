package com.mateuszholik.passwordgenerator.di

import com.mateuszholik.data.di.dataModules
import com.mateuszholik.domain.di.domainModules
import org.koin.core.module.Module

private val appModules = listOf<Module>()

val allModules = dataModules + domainModules + appModules