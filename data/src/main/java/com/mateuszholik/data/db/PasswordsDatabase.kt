package com.mateuszholik.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mateuszholik.data.db.converters.Converters
import com.mateuszholik.data.db.daos.NamesDao
import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.db.models.NamesEntity
import com.mateuszholik.data.db.models.PasswordEntity

@Database(
    entities = [
        NamesEntity::class,
        PasswordEntity::class,
    ],
    version = 3,
    exportSchema = true,
)
@TypeConverters(Converters::class)
internal abstract class PasswordsDatabase : RoomDatabase() {

    abstract fun passwordsDao(): PasswordsDao
    abstract fun namesDao(): NamesDao
}
