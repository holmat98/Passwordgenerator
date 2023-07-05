package com.mateuszholik.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mateuszholik.data.db.converters.Converters
import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.db.models.PasswordDB

@Database(
    entities = [PasswordDB::class],
    version = 3,
    exportSchema = true,
)
@TypeConverters(Converters::class)
internal abstract class PasswordsDatabase : RoomDatabase() {

    abstract fun passwordsDao(): PasswordsDao
}
