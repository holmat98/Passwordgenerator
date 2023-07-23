package com.mateuszholik.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mateuszholik.data.db.converters.Converters
import com.mateuszholik.data.db.daos.NamesDao
import com.mateuszholik.data.db.daos.OldPasswordsDao
import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.db.models.entities.NamesEntity
import com.mateuszholik.data.db.models.entities.OldPasswordEntity
import com.mateuszholik.data.db.models.entities.PasswordEntity
import com.mateuszholik.data.db.models.views.PasswordDetailsView
import com.mateuszholik.data.db.models.views.PasswordInfoView

@Database(
    entities = [
        NamesEntity::class,
        PasswordEntity::class,
        OldPasswordEntity::class,
    ],
    views = [
        PasswordInfoView::class,
        PasswordDetailsView::class,
    ],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 2, to = 3)
    ],
    exportSchema = true,
)
@TypeConverters(Converters::class)
internal abstract class PasswordsDatabase : RoomDatabase() {

    abstract fun oldPasswordsDao(): OldPasswordsDao
    abstract fun namesDao(): NamesDao

    abstract fun passwordsDao(): PasswordsDao
}
