package com.mateuszholik.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mateuszholik.data.db.models.PasswordDB.Companion.COLUMN_PASSWORD_SCORE_NAME
import com.mateuszholik.data.db.models.PasswordDB.Companion.COLUMN_PLATFORM_NAME
import com.mateuszholik.data.db.models.PasswordDB.Companion.COLUMN_WEBSITE_NAME
import com.mateuszholik.data.db.models.PasswordDB.Companion.TABLE_NAME as PASSWORDS_TABLE_NAME

internal val MIGRATION_2_3 = object : Migration(2, 3) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.run {
            execSQL("""ALTER TABLE $PASSWORDS_TABLE_NAME ADD COLUMN $COLUMN_PLATFORM_NAME TEXT NOT NULL DEFAULT ''""")
            execSQL("""ALTER TABLE $PASSWORDS_TABLE_NAME ADD COLUMN $COLUMN_PASSWORD_SCORE_NAME INTEGER NOT NULL DEFAULT 0""")
            execSQL("""ALTER TABLE $PASSWORDS_TABLE_NAME ADD COLUMN $COLUMN_WEBSITE_NAME TEXT NOT NULL DEFAULT ''""")
        }
    }
}
