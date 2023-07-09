package com.mateuszholik.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val MIGRATION_2_3 = object : Migration(2, 3) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.run {
            execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `names` (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                    `column_name` BLOB NOT NULL, 
                    `column_name_iv` BLOB NOT NULL, 
                    `package_name` BLOB, 
                    `package_name_iv` BLOB, 
                    `website` BLOB, `website_iv` BLOB
                    )
                """
            )
            execSQL("""ALTER TABLE passwords ADD COLUMN platform_name_id INTEGER NOT NULL DEFAULT 0""")
            execSQL("""ALTER TABLE passwords ADD COLUMN password_score INTEGER NOT NULL DEFAULT 0""")
        }
    }
}
