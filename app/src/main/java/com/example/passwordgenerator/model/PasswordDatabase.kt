package com.example.passwordgenerator.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.passwordgenerator.model.entities.Password
import com.example.passwordgenerator.model.entities.PasswordDao

@Database(entities = [Password::class], version = 1, exportSchema = false)
abstract class PasswordDatabase: RoomDatabase(){
    abstract fun passwordDao(): PasswordDao

    companion object{
        @Volatile
        private var INSTANCE: PasswordDatabase? = null

        fun getDatabase(context: Context): PasswordDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            else{
                synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PasswordDatabase::class.java,
                        "passwordDatabase"
                    ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}