package com.mateuszholik.data.di

import com.mateuszholik.data.di.modules.databaseModule
import com.mateuszholik.data.di.modules.mappersModule
import com.mateuszholik.data.di.modules.repositoriesModule

val dataModules = listOf(
    databaseModule,
    mappersModule,
    repositoriesModule
)