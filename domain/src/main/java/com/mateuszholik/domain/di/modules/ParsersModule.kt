package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.parsers.PasswordsParser
import com.mateuszholik.domain.parsers.PasswordsParserImpl
import org.koin.dsl.module

internal val parsersModule = module {

    single<PasswordsParser> { PasswordsParserImpl() }
}