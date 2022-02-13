package com.mateuszholik.passwordgenerator.model.entities

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(password: Password)

    @Delete
    suspend fun delete(password: Password)

    @Update
    suspend fun update(password: Password)

    @Query("select * from passwords_Table")
    fun getAllPasswords() : LiveData<List<Password>>
}