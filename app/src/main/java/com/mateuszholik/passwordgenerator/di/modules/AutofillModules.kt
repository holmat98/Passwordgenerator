package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.autofill.builders.FillResponseBuilder
import com.mateuszholik.passwordgenerator.autofill.factories.DatasetFactory
import com.mateuszholik.passwordgenerator.autofill.factories.DatasetFactoryImpl
import com.mateuszholik.passwordgenerator.autofill.factories.PresentationsFactory
import com.mateuszholik.passwordgenerator.autofill.factories.PresentationsFactoryImpl
import com.mateuszholik.passwordgenerator.autofill.factories.RemoteViewsFactory
import com.mateuszholik.passwordgenerator.autofill.factories.RemoteViewsFactoryImpl
import com.mateuszholik.passwordgenerator.autofill.parsers.StructureParser
import org.koin.dsl.module

val autofillModule = module {

    single<RemoteViewsFactory> { RemoteViewsFactoryImpl() }

    single<PresentationsFactory> { PresentationsFactoryImpl(remoteViewsFactory = get()) }

    single<DatasetFactory> {
        DatasetFactoryImpl(
            remoteViewsFactory = get()
        )
    }

    single {
        FillResponseBuilder(
            datasetFactory = get(),
            remoteViewsFactory = get(),
            presentationsFactory = get()
        )
    }

    single { StructureParser() }
}
