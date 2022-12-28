package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.parsers.JsonParser
import com.mateuszholik.domain.parsers.JsonParserImpl
import org.koin.dsl.module

internal val parsersModule = module {

    single<JsonParser> { JsonParserImpl() }
}