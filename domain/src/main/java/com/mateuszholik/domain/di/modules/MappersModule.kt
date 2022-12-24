package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.mappers.NewPasswordMapper
import com.mateuszholik.domain.mappers.NewPasswordMapperImpl
import com.mateuszholik.domain.mappers.UpdatedPasswordMapper
import com.mateuszholik.domain.mappers.UpdatedPasswordMapperImpl
import org.koin.dsl.module

internal val mappersModule = module {

    single<NewPasswordMapper> { NewPasswordMapperImpl() }

    single<UpdatedPasswordMapper> { UpdatedPasswordMapperImpl() }
}