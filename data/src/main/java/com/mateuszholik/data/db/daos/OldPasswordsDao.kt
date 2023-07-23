package com.mateuszholik.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.mateuszholik.data.db.models.entities.OldPasswordEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
internal interface OldPasswordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndGetId(oldPasswordEntity: OldPasswordEntity): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPasswords(passwords: List<OldPasswordEntity>): Completable

    @Update
    fun update(oldPasswordEntity: OldPasswordEntity): Completable

    @Query("select * from passwords where id = :passwordId")
    fun getPassword(passwordId: Long): Maybe<OldPasswordEntity>

    @Query("select * from passwords")
    fun getAllPasswords(): Single<List<OldPasswordEntity>>

    @Query("delete from passwords where id = :passwordId")
    fun deletePassword(passwordId: Long): Completable
}
