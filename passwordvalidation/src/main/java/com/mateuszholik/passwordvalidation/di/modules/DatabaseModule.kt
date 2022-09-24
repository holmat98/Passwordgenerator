package com.mateuszholik.passwordvalidation.di.modules

import android.content.Context
import androidx.room.Room
import com.mateuszholik.passwordvalidation.R
import com.mateuszholik.passwordvalidation.db.CommonDataDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val databaseModule = module {

    single {
        provideDatabase(androidContext())
    }

    single {
        get<CommonDataDatabase>().commonNameDao()
    }

    single {
        get<CommonDataDatabase>().commonPasswordDao()
    }

    single {
        get<CommonDataDatabase>().commonWordDao()
    }

    single {
        get<CommonDataDatabase>().commonPetName()
    }

}

private fun provideDatabase(context: Context) =
    Room.databaseBuilder(
        context.applicationContext,
        CommonDataDatabase::class.java,
        "common_data"
    )
        .createFromAsset("common_data.db")
        .build()