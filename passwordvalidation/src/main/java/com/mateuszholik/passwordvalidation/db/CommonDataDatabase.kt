package com.mateuszholik.passwordvalidation.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mateuszholik.passwordvalidation.db.daos.CommonNameDao
import com.mateuszholik.passwordvalidation.db.daos.CommonPasswordDao
import com.mateuszholik.passwordvalidation.db.daos.CommonPetsNameDao
import com.mateuszholik.passwordvalidation.db.daos.CommonWordDao
import com.mateuszholik.passwordvalidation.db.models.CommonName
import com.mateuszholik.passwordvalidation.db.models.CommonPassword
import com.mateuszholik.passwordvalidation.db.models.CommonPetName
import com.mateuszholik.passwordvalidation.db.models.CommonWord

@Database(
    entities = [
        CommonPassword::class,
        CommonWord::class,
        CommonName::class,
        CommonPetName::class
    ],
    exportSchema = false,
    version = 1
)
internal abstract class CommonDataDatabase : RoomDatabase() {

    abstract fun commonPasswordDao(): CommonPasswordDao
    abstract fun commonWordDao(): CommonWordDao
    abstract fun commonNameDao(): CommonNameDao
    abstract fun commonPetName(): CommonPetsNameDao
}
