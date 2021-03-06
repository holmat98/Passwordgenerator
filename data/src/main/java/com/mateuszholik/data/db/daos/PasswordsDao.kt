package com.mateuszholik.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.mateuszholik.data.db.models.PasswordDB
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
internal interface PasswordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(passwordDB: PasswordDB): Completable

    @Update
    fun update(passwordDB: PasswordDB): Completable

    @Query("select * from passwords")
    fun getAllPasswords(): Single<List<PasswordDB>>

    @Query("delete from passwords where id = :passwordId")
    fun deletePassword(passwordId: Long): Completable
}