package com.mateuszholik.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.mateuszholik.data.db.models.PasswordEntity
import com.mateuszholik.data.db.models.PasswordEntity.Companion.COLUMN_ID_NAME
import com.mateuszholik.data.db.models.PasswordEntity.Companion.COLUMN_NAME_FOREIGN_KEY_ID
import com.mateuszholik.data.db.models.PasswordEntity.Companion.COLUMN_PASSWORD_SCORE_NAME
import com.mateuszholik.data.db.models.PasswordEntity.Companion.TABLE_NAME
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
internal interface PasswordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndGetId(passwordEntity: PasswordEntity): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPasswords(passwords: List<PasswordEntity>): Completable

    @Update
    fun update(passwordEntity: PasswordEntity): Completable

    @Query("select * from passwords where id = :passwordId")
    fun getPassword(passwordId: Long): Maybe<PasswordEntity>

    @Query("select * from passwords")
    fun getAllPasswords(): Single<List<PasswordEntity>>

    @Query("delete from passwords where id = :passwordId")
    fun deletePassword(passwordId: Long): Completable

    /**
     * This method is supposed to be used only for updating the database to the correct state
     * after migrating from version 2 to 3
     *//*
    @Query(
        """
            UPDATE $TABLE_NAME SET
            $COLUMN_NAME_FOREIGN_KEY_ID = :nameId
            $COLUMN_PASSWORD_SCORE_NAME = :passwordScore
            WHERE $COLUMN_ID_NAME = :id
        """
    )
    fun updatePlatformAndPasswordScoreFor(
        id: Long,
        nameId: Long,
        passwordScore: Int,
    ): Completable*/
}
