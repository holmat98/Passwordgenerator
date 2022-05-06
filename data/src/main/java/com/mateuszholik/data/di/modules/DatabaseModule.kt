package com.mateuszholik.data.di.modules

import android.content.Context
import androidx.room.Room
import com.mateuszholik.data.db.PasswordsDatabase
import com.mateuszholik.data.db.converters.Converters
import com.mateuszholik.data.di.models.Constants.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val databaseModule = module {

    single {
        Converters()
    }

    single {
        provideDatabase(
            context = androidContext(),
            converter = get()
        )
    }

    single {
        get<PasswordsDatabase>().passwordsDao()
    }
}

private fun provideDatabase(
    context: Context,
    converter: Converters
) = Room.databaseBuilder(
    context.applicationContext,
    PasswordsDatabase::class.java,
    DATABASE_NAME
)
    .addTypeConverter(converter)
    .build()