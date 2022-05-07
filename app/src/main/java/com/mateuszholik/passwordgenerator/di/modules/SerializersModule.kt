package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.serializers.LocalDateTimeDeserializer
import com.mateuszholik.passwordgenerator.serializers.LocalDateTimeSerializer
import org.koin.dsl.module

val serializersModule = module {

    single {
        LocalDateTimeSerializer()
    }

    single {
        LocalDateTimeDeserializer()
    }
}