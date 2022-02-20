package com.mateuszholik.data.di.modules

import android.content.Context
import androidx.room.Room
import com.mateuszholik.data.db.PasswordsDatabase
import com.mateuszholik.data.db.converters.DateConverter
import com.mateuszholik.data.di.models.Constants.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val databaseModule = module {

    single {
        DateConverter()
    }

    single {
        provideDatabase(androidContext(), get())
    }

    single {
        get<PasswordsDatabase>().passwordsDao()
    }
}

private fun provideDatabase(context: Context, dateConverter: DateConverter) = Room.databaseBuilder(
    context.applicationContext,
    PasswordsDatabase::class.java,
    DATABASE_NAME
)
    .addTypeConverter(dateConverter)
    .build()