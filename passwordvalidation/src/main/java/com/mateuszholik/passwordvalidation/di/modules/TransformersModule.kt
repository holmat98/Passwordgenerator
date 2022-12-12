package com.mateuszholik.passwordvalidation.di.modules

import com.mateuszholik.passwordvalidation.transformers.StringTransformer
import com.mateuszholik.passwordvalidation.transformers.StringTransformerImpl
import org.koin.dsl.module

internal val transformersModule = module {

    single<StringTransformer> { StringTransformerImpl() }
}