package com.mateuszholik.passwordgenerator.di.modules

import android.os.Build
import com.mateuszholik.passwordgenerator.autofill.builders.FillResponseBuilder
import com.mateuszholik.passwordgenerator.autofill.factories.DatasetFactoryImpl
import com.mateuszholik.passwordgenerator.autofill.factories.PresentationsFactory
import com.mateuszholik.passwordgenerator.autofill.factories.PresentationsFactoryImpl
import com.mateuszholik.passwordgenerator.autofill.factories.RemoteViewsFactory
import com.mateuszholik.passwordgenerator.autofill.factories.RemoteViewsFactoryImpl
import com.mateuszholik.passwordgenerator.autofill.parsers.StructureParser
import com.mateuszholik.passwordgenerator.autofill.factories.Sdk33DatasetFactoryImpl
import org.koin.dsl.module

val autofillModule = module {

    single<RemoteViewsFactory> { RemoteViewsFactoryImpl() }

    single<PresentationsFactory> { PresentationsFactoryImpl(remoteViewsFactory = get()) }

    single {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            DatasetFactoryImpl(
                remoteViewsFactory = get()
            )
        } else {
            Sdk33DatasetFactoryImpl(
                presentationsFactory = get()
            )
        }
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
